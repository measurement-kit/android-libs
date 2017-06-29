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
#include <measurement_kit/common/non_copyable.hpp>
#include <measurement_kit/common/non_movable.hpp>
#include <set>
#include <stdexcept>

class Environment : public mk::NonCopyable, public mk::NonMovable {
  public:
    Environment();

    JNIEnv *get_jni_env() noexcept;

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
};

#endif // SWIG
#endif // MK_JNI_WRAPPERS_ENVIRONMENT__HPP
