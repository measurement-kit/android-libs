// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "saved_jvm.hpp"

#include <stdexcept>

/*
    The purpose of this exercise is to load the JavaVM when the dynamic
    library is loaded so we can reference it later. This allows us to get
    the current thread (if we are on a Java thread), or to attach a Java
    thread to the current C++ thread (otherwise).
*/

static JavaVM *java_vm = nullptr;

JavaVM *mk_jni_get_saved_jvm() {
    if (!java_vm) {
        throw std::runtime_error("null pointer exception");
    }
    return java_vm;
}

extern "C" jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    java_vm = vm;
    return JNI_VERSION_1_6;
}
