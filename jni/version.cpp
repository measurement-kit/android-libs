// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "mkall.h"

#include <measurement_kit/common/version.h>

#include "mkall_util.h"

JNIEXPORT jstring JNICALL
Java_io_ooni_mk_MKVersion_getVersionMK(JNIEnv *env, jclass) {
  return env->NewStringUTF(MK_VERSION);
}
