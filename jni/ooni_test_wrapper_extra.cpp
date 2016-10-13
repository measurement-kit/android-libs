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
                throw std::runtime_error("cannot get environment");
            }
            is_attached_ = true;
        } else if (err != JNI_OK) {
            throw std::runtime_error("cannot get environment");
        } else {
            /* nothing */ ;
        }
    }

    JNIEnv *operator->() {
        if (env_ == nullptr) { // Check here only for robustness to refactoring
            throw std::runtime_error("null pointer exception");
        }
        return env_;
    }

    ~Environment() {
        // Null check here only for robustness to refactoring
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
    Environment environ; // Throws on error
    jobject global_cb = environ->NewGlobalRef(delegate); // Keep safe
    if (global_cb == nullptr) {
        return;
    }
    real_test_->logger->on_eof([global_cb]() {
        Environment environ; // Throws on error
        environ->DeleteGlobalRef(global_cb);
    });
    real_test_->on_log([global_cb](uint32_t severity, const char *message) {
        Environment environ; // Throws on error
        jlong java_severity = severity;
        jstring java_message = environ->NewStringUTF(message);
        if (!java_message) {
            return;
        }
        jclass clazz = environ->GetObjectClass(global_cb);
        if (!clazz) {
            return;
        }
        jmethodID meth_id = environ->GetMethodID(clazz, "callback",
                "(JLjava/lang/String;)V");
        if (!meth_id) {
            return;
        }
        environ->CallVoidMethod(global_cb, meth_id, java_severity,
                                java_message);
        // Note: sure the above function could cause exceptions but
        // my understanding is that it will be raised when we return
        // back to Java, so I don't know what should we do here
    });
}

void OoniTestWrapper::run(jobject callback) {
    Environment environ; // Throws on error
    jobject global_cb = environ->NewGlobalRef(callback); // Keep safe
    if (global_cb == nullptr) {
        return;
    }
    real_test_->run([global_cb]() {
        Environment environ; // Throws on error
        jclass clazz = environ->GetObjectClass(global_cb);
        if (!clazz) {
            environ->DeleteGlobalRef(global_cb);
            return;
        }
        jmethodID meth_id = environ->GetMethodID(clazz, "callback", "()V");
        if (!meth_id) {
            environ->DeleteGlobalRef(global_cb);
            return;
        }
        environ->CallVoidMethod(global_cb, meth_id);
        environ->DeleteGlobalRef(global_cb);
    });
}

void OoniTestWrapper::on_entry(jobject delegate) {
    Environment environ; // Throws on error
    jobject global_cb = environ->NewGlobalRef(delegate); // Keep safe
    if (global_cb == nullptr) {
        return;
    }
    real_test_->on_end([global_cb]() {
        Environment environ; // Throws on error
        environ->DeleteGlobalRef(global_cb);
    });
    real_test_->on_entry([global_cb](std::string entry) {
        Environment environ; // Throws on error
        jstring java_entry = environ->NewStringUTF(entry.c_str());
        if (!java_entry) {
            return;
        }
        jclass clazz = environ->GetObjectClass(global_cb);
        if (!clazz) {
            return;
        }
        jmethodID meth_id = environ->GetMethodID(clazz, "callback",
                "(Ljava/lang/String;)V");
        if (!meth_id) {
            return;
        }
        environ->CallVoidMethod(global_cb, meth_id, java_entry);
        // Note: sure the above function could cause exceptions but
        // my understanding is that it will be raised when we return
        // back to Java, so I don't know what should we do here
    });
}
