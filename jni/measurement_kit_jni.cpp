// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include <jni.h>
#include <measurement_kit/ooni.hpp>
#include <string>
#include "io_github_measurement_kit_jni_sync_OONI.h"

static std::string jstring_to_cxxstring(JNIEnv *env, jstring source) {
    const char *ptr = env->GetStringUTFChars(source, nullptr);
    if (ptr == nullptr) throw std::bad_alloc();
    std::string copy = ptr;
    env->ReleaseStringUTFChars(source, ptr);
    return copy;
}

JNIEXPORT jstring JNICALL
Java_io_github_measurement_1kit_jni_sync_OONI_dnsInjection(
    JNIEnv *env, jclass /*clazz*/, jstring backend, jstring inputPath,
    jboolean /*verbose*/, jstring logPath) {
    try {
        mk::ooni::DnsInjectionTest()
            .set_backend(jstring_to_cxxstring(env, backend))
            .set_input_file_path(jstring_to_cxxstring(env, inputPath))
            .set_verbose()
            .run();
    } catch (...) {
        // XXX suppress
    }
    return nullptr;
}

JNIEXPORT jstring JNICALL
Java_io_github_measurement_1kit_jni_sync_OONI_httpInvalidRequestLine(
    JNIEnv *env, jclass /*clazz*/, jstring backend, jboolean /*verbose*/,
    jstring logPath) {
    try {
        mk::ooni::HttpInvalidRequestLineTest()
            .set_backend(jstring_to_cxxstring(env, backend))
            .set_verbose()
            .run();
    } catch (...) {
        // XXX suppress
    }
    return nullptr;
}

JNIEXPORT jstring JNICALL
Java_io_github_measurement_1kit_jni_sync_OONI_tcpConnect(
    JNIEnv *env, jclass /*clazz*/, jstring port, jstring inputPath,
    jboolean verbose, jstring logPath) {
    try {
        mk::ooni::TcpConnectTest()
            .set_port(jstring_to_cxxstring(env, port))
            .set_input_file_path(jstring_to_cxxstring(env, inputPath))
            .set_verbose()
            .run();
    } catch (...) {
        // XXX suppress
    }
    return nullptr;
}
