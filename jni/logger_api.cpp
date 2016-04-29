// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include <android/log.h>
#include <jni.h>
#include <measurement_kit/common.hpp>
#include <string>
#include "org_openobservatory_measurement_kit_jni_LoggerApi.h"

JNIEXPORT void JNICALL
Java_org_openobservatory_measurement_1kit_jni_LoggerApi_setVerbose
  (JNIEnv *, jclass, jint verbose) {
    try {
        mk::set_verbose(verbose);
    } catch (...) {
        // XXX suppress
    }
}

JNIEXPORT void JNICALL
Java_org_openobservatory_measurement_1kit_jni_LoggerApi_useAndroidLogger
  (JNIEnv *, jclass) {
    try {
        mk::on_log([](const char *s) {
            __android_log_print(ANDROID_LOG_INFO, "default-logger", "%s", s);
        });
    } catch (...) {
        // XXX suppress
    }
}
