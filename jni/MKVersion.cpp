// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mk-jni-api.h"
#include "mk-jni-util.hpp"

#include "measurement_kit/common/version.h"

JNIEXPORT jstring JNICALL
Java_io_ooni_mk_MKVersion_getVersion(JNIEnv *env, jclass) {
  return env->NewStringUTF(MK_VERSION);
}
