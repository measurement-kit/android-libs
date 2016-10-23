// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#ifndef JNI_COMMON_HPP
#define JNI_COMMON_HPP

#include <jni.h>
#include <string>

namespace mk {
namespace jni {

std::string cxxstring(JNIEnv *env, jstring source);

} // namespace jni
} // namespace mk

#endif
