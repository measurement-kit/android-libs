// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
#ifndef MK_JNI_WRAPPERS_ENVIRONMENT__HPP
#define MK_JNI_WRAPPERS_ENVIRONMENT__HPP
#ifndef SWIG

#include <algorithm>
#include <cstdlib>
#include <exception>
#include <functional> // IWYU pragma: keep
#include <jni.h>
#include <set>
#include <stdexcept>

#include "non_copyable.hpp"
#include "non_movable.hpp"

class Environment : public mk::NonCopyable, public mk::NonMovable {
  public:
    Environment();

    JNIEnv *get_jni_env() noexcept;

    /*
        Memory management strategy: in most cases, we don't need to care
        about local references. Especially when we have a callback just
        called one time when an operation is complete, we can rely on the
        JVM to remove local references when native code returns.

        Classes returned by `find_class` are also local references and
        we mostly do not make globals out of them. Method IDs are AFAICT
        offsets that depend on the specific class and do not need to be
        kept alive (anyway, we do not do that).

        The only case where we need to exercise care is for callbacks
        called repeatedly. For example, the `on_log` callback bound to
        all the nettests of MK. In that case, we need to clear the
        arguments passed to callbacks after the callbacks return, so
        to ensure that we don't use too many local reference slots.

        For this reason, we've added `own_local`. This function however
        should be used sparingly, as explained above it's not needed
        in most cases.

        A previous implementation used PushLocalFrame and PopLocalFrame, but
        our usage of it was too aggressive and also cleaned up variables
        that were meant to be passed to callbacks.
    */

    jstring own_local(jstring);
    jclass own_local(jclass);
    jobject own_local(jobject);
    void own_global(jobject);

    template <typename Callable, typename... Args>
    void trap_and_route_exceptions(Callable &&f, Args &&... args) {
        try {
            f(std::forward<Args>(args)...);
        } catch (const std::exception &exc) {
            set_java_exception_(exc);
        } catch (...) {
            set_java_exception_(std::runtime_error("unhandled error"));
        }
    }

    template <typename Callable, typename... Args>
    auto trap_route_exceptions_and_return(Callable &&f, Args &&... args) {
        try {
            return f(std::forward<Args>(args)...);
        } catch (const std::exception &exc) {
            set_java_exception_(exc);
            return decltype(f(args...)){};
        } catch (...) {
            set_java_exception_(std::runtime_error("unhandled error"));
            return decltype(f(args...)){};
        }
    }

    jclass get_object_class(jobject obj);
    jmethodID get_method_id(jclass clazz, const char *name, const char *sig);
    jobject new_global_ref(jobject obj);
    jclass find_class(const char *class_name);
    jobject new_object(jclass clazz, jmethodID mid, ...);
    void call_void_method(jobject obj, jmethodID mid, ...);
    jobject call_object_method(jobject obj, jmethodID mid, ...);
    jboolean call_boolean_method(jobject obj, jmethodID mid, ...);
    jstring new_string_utf(const char *);

    template <typename Callable>
    void with_string_utf_chars(jstring js, Callable &&callable) {
        JNIEnv *env = get_jni_env();
        auto s = env->GetStringUTFChars(js, nullptr);
        if (s == nullptr) {
            throw std::runtime_error("GetStringUTFChars() failed");
        }
        try {
            callable(s);
        } catch (...) {
            env->ReleaseStringUTFChars(js, s);
            throw;
        }
        env->ReleaseStringUTFChars(js, s);
    }

    ~Environment();

  private:
    void set_java_exception_(const std::exception &exc) {
        JNIEnv *env = get_jni_env();
        if (env->ExceptionCheck()) {
            return;
        }
        auto clazz = env->FindClass("java/lang/Exception");
        if (env->ThrowNew(clazz, exc.what()) != 0) {
            std::abort();
        }
    }

    JNIEnv *env___ = nullptr;
    bool is_attached_ = false;
    std::set<jobject> globals_;
    std::set<jobject> locals_;
};

#endif // SWIG
#endif // MK_JNI_WRAPPERS_ENVIRONMENT__HPP
