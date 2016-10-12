// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "ooni_test_wrapper.hpp"
#include "saved_jvm.hpp"

class Environment {
  public:
    // Implementation note: Assume that we're in the most general case where
    // the callback has been called in another thread context, discover whether
    // we have a Java thread attached to it, otherwise create a temporary one

    Environment() {
        JavaVM *vm = mk_jni_get_saved_jvm(); // Throws on failure
        int err = vm->GetEnv((void **)&env_, JNI_VERSION_1_6);
        if (err == JNI_EDETACHED) {
            if (vm->AttachCurrentThread(&env_, nullptr) != 0) {
                env_ = nullptr; // Robustness
                return;
            }
            is_attached_ = true;
        } else if (err != JNI_OK) {
            env_ = nullptr; // Robustness
        } else {
            /* nothing */
        }
    }

    operator bool() { return env_ != nullptr; }

    JNIEnv *operator->() {
        if (env_ == nullptr) {
            throw std::runtime_error("null pointer exception");
        }
        return env_;
    }

    ~Environment() {
        if (is_attached_ && env_ != nullptr) {
            JavaVM *vm = mk_jni_get_saved_jvm(); // Throws on failure
            vm->DetachCurrentThread();
        }
    }

  private:
    JNIEnv *env_ = nullptr;
    bool is_attached_ = false;
};

void OoniTestWrapper::on_log(jobject delegate) {
    Environment environ;
    if (!environ) {
        return;
    }
    log_cb_ = environ->NewGlobalRef(delegate); // Keep safe
    if (log_cb_ == nullptr) {
        return;
    }
    real_test_->on_log([this](uint32_t severity, const char *message) {
        // Note: as stated above, we don't know in which thread we are running
        // and whether it's attached to JVM, thus we follow the most robust and
        // general approach implemented in the `Environment` class
        assert(log_cb_ != nullptr);
        Environment environ;
        if (!environ) {
            return;
        }
        jlong java_severity = severity;
        jstring java_message = environ->NewStringUTF(message);
        if (!java_message) {
            return;
        }
        jclass clazz = environ->GetObjectClass(log_cb_);
        if (!clazz) {
            return;
        }
        jmethodID meth_id = environ->GetMethodID(clazz, "callback",
                "(JLjava/lang/String;)V");
        if (!meth_id) {
            return;
        }
        environ->CallVoidMethod(log_cb_, meth_id, java_severity, java_message);
        // Note: sure the above function could cause exceptions but
        // my understanding is that it will be raised when we return
        // back to Java, so I don't know what should we do here
    });
}

OoniTestWrapper::~OoniTestWrapper() {
    if (log_cb_ != nullptr) {
        Environment environ;
        if (!environ) {
            return;
        }
        environ->DeleteGlobalRef(log_cb_); // Forget
        log_cb_ = nullptr;
    }
}
