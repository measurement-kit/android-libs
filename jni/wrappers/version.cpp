// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include <jni.h>
#include <measurement_kit/common.hpp>
#include "org_openobservatory_measurement_kit_Version.h"

JNIEXPORT jstring JNICALL
Java_org_openobservatory_measurement_1kit_Version_getVersion
  (JNIEnv *env, jclass) {
    return env->NewStringUTF(mk_version());
}
