// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
// =============================================================
// Borrows from mapbox/jni.hpp <https://github.com/mapbox/jni.hpp>
// Copyright © 2016, Mapbox
//
// Permission to use, copy, modify, and/or distribute this software for any
// purpose with or without fee is hereby granted, provided that the above
// copyright notice and this permission notice appear in all copies.
//
// THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
// REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
// AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
// INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
// LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
// OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
// PERFORMANCE OF THIS SOFTWARE.

#include "environment_.hpp"
#include <jni.h>
#include <map>
#include <sstream>
#include <stdexcept>
#include <vector>

// Get saved JVM

/*
    The purpose of this exercise is to load the JavaVM when the dynamic
    library is loaded so we can reference it later. This allows us to get
    the current thread (if we are on a Java thread), or to attach a Java
    thread to the current C++ thread (otherwise).
*/

static JavaVM *mk_java_vm = nullptr;

static JavaVM *get_saved_jvm() {
    if (!mk_java_vm) {
        // Should not happen, but we want to be robust
        std::abort();
    }
    return mk_java_vm;
}

// Assumption: JNI_OnLoad() will be called only once by the JVM (this seems
// very reasonable as an assumption, actually).

static const std::vector<std::string> &cached_names() {
    static std::vector<std::string> v{
        "org/openobservatory/measurement_kit/swig/Error",
        "org/openobservatory/measurement_kit/swig/OrchestrateAuth"
    };
    return v;
}

static std::map<std::string, jclass> &cached_classes() {
    static std::map<std::string, jclass> m;
    return m;
}

extern "C" jint JNI_OnLoad(JavaVM *vm, void *) {
    mk_java_vm = vm;

    // Load and keep alive classes that we will need in callbacks because the
    // Android JVM cannot load user defined classes from native threads.
    //
    // See: https://stackoverflow.com/questions/13263340
    Environment environ;
    for (auto &s : cached_names()) {
        // Note: `find_class` and `new_global_ref` throw on error
        auto local = environ.find_class(s.c_str());
        // Note: we need to make the reference global such that the class
        // cannot be swapped out by the JVM.
        //
        // Performance note: it would probably be optimal to cache much
        // more than some classes. This is something we can do in some
        // future refactoring effort.
        //
        // See: https://stackoverflow.com/questions/10617735
        cached_classes()[s] = (jclass)environ.new_global_ref((jobject)local);
    }

    return JNI_VERSION_1_6;
}

// Environment

// Implementation note: Assume that we're in the most general case where
// the callback has been called in another thread context, discover whether
// we have a Java thread attached to it, otherwise create a temporary one

// Some implementations type the parameter as JNIEnv**, others as void**.
// See https://bugs.openjdk.java.net/browse/JDK-6569899
class jni_env_cast {
  public:
    void **operator()(JNIEnv **env, jint (JavaVM::*)(void **, void *)) {
        return reinterpret_cast<void **>(env);
    }
    void **operator()(JNIEnv **env, jint (JavaVM::*)(void **, jint)) {
        return reinterpret_cast<void **>(env);
    }

    JNIEnv **operator()(JNIEnv **env, jint (JavaVM::*)(JNIEnv **, void *)) {
        return env;
    }
    JNIEnv **operator()(JNIEnv **env, jint (JavaVM::*)(JNIEnv **, jint)) {
        return env;
    }
};

Environment::Environment() {
    JavaVM *vm = get_saved_jvm(); // Aborts on failure
    /*
     * Using reinterpret_cast<void **> as shown here:
     * https://developer.android.com/training/articles/perf-jni.html
     */
    int err = vm->GetEnv(jni_env_cast()(&env___, &JavaVM::GetEnv),
                         JNI_VERSION_1_6);
    if (err == JNI_EDETACHED) {
        if (vm->AttachCurrentThread(jni_env_cast()(
                &env___, &JavaVM::AttachCurrentThread), nullptr) != 0) {
            std::abort();
        }
        is_attached_ = true;
    } else if (err != JNI_OK) {
        std::abort();
    } else {
        /* nothing */;
    }
    if (env___->PushLocalFrame(64) != 0) {
        std::abort();
    }
}

JNIEnv *Environment::get_jni_env() noexcept {
    if (env___ == nullptr) {
        // Should not happen, but we want to be robust
        std::abort();
    }
    return env___;
}

void Environment::own_global(jobject object) { globals_.insert(object); }

jclass Environment::get_object_class(jobject obj) {
    // My understanding is that this specific API can't fail
    return get_jni_env()->GetObjectClass(obj);
}

jmethodID Environment::get_method_id(jclass clazz, const char *name,
                                     const char *sig) {
    auto env = get_jni_env();
    auto method_id = env->GetMethodID(clazz, name, sig);
    if (method_id == nullptr) {
        std::stringstream ss;
        ss << "Method id '" << name << "' with signature '" << sig
           << "': not found";
        throw std::runtime_error(ss.str());
    }
    return method_id;
}

jobject Environment::new_global_ref(jobject obj) {
    auto env = get_jni_env();
    auto global = env->NewGlobalRef(obj);
    if (global == nullptr) {
        throw std::runtime_error("failed to create global ref");
    }
    return global;
}

jclass Environment::find_class(const char *class_name) {
    // Search in cache first, then proceed with the normal method
    if (cached_classes().count(class_name) != 0) {
        return cached_classes().at(class_name);
    }
    // Under Android, the following will fail for non system classes that
    // you attempt to load from native threads later attached to a JVM.
    //
    // The reason why this happens is documented above.
    auto env = get_jni_env();
    auto clazz = env->FindClass(class_name);
    if (clazz == nullptr) {
        std::stringstream ss;
        ss << "cannot find class: " << class_name;
        throw std::runtime_error(ss.str());
    }
    return clazz;
}

jobject Environment::new_object(jclass clazz, jmethodID mid, ...) {
    auto env = get_jni_env();
    va_list ap;
    va_start(ap, mid);
    auto object = env->NewObjectV(clazz, mid, ap);
    va_end(ap);
    if (object == nullptr) {
        throw std::runtime_error("NewObject() failed");
    }
    return object;
}

void Environment::call_void_method(jobject obj, jmethodID mid, ...) {
    auto env = get_jni_env();
    va_list ap;
    va_start(ap, mid);
    env->CallVoidMethodV(obj, mid, ap);
    if (env->ExceptionCheck()) {
        throw std::runtime_error("CallVoidMethod() failed");
    }
}

jobject Environment::call_object_method(jobject obj, jmethodID mid, ...) {
    auto env = get_jni_env();
    va_list ap;
    va_start(ap, mid);
    auto object = env->CallObjectMethodV(obj, mid, ap);
    if (env->ExceptionCheck()) {
        throw std::runtime_error("CallObjectMethod() failed");
    }
    return object;
}

jboolean Environment::call_boolean_method(jobject obj, jmethodID mid, ...) {
    auto env = get_jni_env();
    va_list ap;
    va_start(ap, mid);
    auto boolean = env->CallBooleanMethodV(obj, mid, ap);
    if (env->ExceptionCheck()) {
        throw std::runtime_error("CallBooleanMethod() failed");
    }
    return boolean;
}

jstring Environment::new_string_utf(const char *s) {
    auto env = get_jni_env();
    auto string = env->NewStringUTF(s);
    if (string == nullptr) {
        throw std::runtime_error("NewStringUTF() failed");
    }
    return string;
}

Environment::~Environment() {
    if (env___ != nullptr) {
        for (auto object : globals_) {
            env___->DeleteGlobalRef(object);
        }
        // FIXME: trying to understand issues with Android
        //(void)env___->PopLocalFrame(nullptr);
    }
    // Null check here only for robustness to refactoring
    if (is_attached_ && env___ != nullptr) {
        JavaVM *vm = get_saved_jvm(); // Throws on failure
        vm->DetachCurrentThread();
    }
}
