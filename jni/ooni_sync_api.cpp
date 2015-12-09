// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include <jni.h>
#include <measurement_kit/ooni.hpp>
#include <string>
#include "common.hpp"
#include "io_github_measurement_kit_jni_sync_OoniSyncApi.h"

JNIEXPORT jstring JNICALL
Java_io_github_measurement_1kit_jni_sync_OoniSyncApi_dnsInjection(
    JNIEnv *env, jclass /*clazz*/, jstring backend, jstring inputPath,
    jboolean /*verbose*/, jstring logPath) {
    try {
        mk::ooni::DnsInjectionTest()
            .set_backend(mk::jni::cxxstring(env, backend))
            .set_input_file_path(mk::jni::cxxstring(env, inputPath))
            .set_verbose()
            .run();
    } catch (...) {
        // XXX suppress
    }
    return nullptr;
}

JNIEXPORT jstring JNICALL
Java_io_github_measurement_1kit_jni_sync_OoniSyncApi_httpInvalidRequestLine(
    JNIEnv *env, jclass /*clazz*/, jstring backend, jboolean /*verbose*/,
    jstring logPath) {
    try {
        mk::ooni::HttpInvalidRequestLineTest()
            .set_backend(mk::jni::cxxstring(env, backend))
            .set_verbose()
            .run();
    } catch (...) {
        // XXX suppress
    }
    return nullptr;
}

JNIEXPORT jstring JNICALL
Java_io_github_measurement_1kit_jni_sync_OoniSyncApi_tcpConnect(
    JNIEnv *env, jclass /*clazz*/, jstring port, jstring inputPath,
    jboolean verbose, jstring logPath) {
    try {
        mk::ooni::TcpConnectTest()
            .set_port(mk::jni::cxxstring(env, port))
            .set_input_file_path(mk::jni::cxxstring(env, inputPath))
            .set_verbose()
            .run();
    } catch (...) {
        // XXX suppress
    }
    return nullptr;
}
