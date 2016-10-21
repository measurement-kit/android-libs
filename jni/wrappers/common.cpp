// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include <jni.h>
#include <string>
#include "common.hpp"

namespace mk {
namespace jni {

std::string cxxstring(JNIEnv *env, jstring source) {
    const char *ptr = env->GetStringUTFChars(source, nullptr);
    if (ptr == nullptr) throw std::bad_alloc();
    std::string copy = ptr;
    env->ReleaseStringUTFChars(source, ptr);
    return copy;
}

} // namespace jni
} // namespace mk
