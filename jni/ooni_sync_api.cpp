// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include <android/log.h>
#include <jni.h>
#include <measurement_kit/ooni.hpp>
#include <stdio.h>
#include <string>
#include <unistd.h>
#include "common.hpp"
#include "org_openobservatory_measurement_kit_jni_sync_OoniSyncApi.h"

class LogFile {
  public:
    LogFile(JNIEnv *ep, jstring lp) {
        logPath = mk::jni::cxxstring(ep, lp);
    }

    LogFile(LogFile &) = delete;
    LogFile &operator=(LogFile &) = delete;
    LogFile(LogFile &&) = delete;
    LogFile &operator=(LogFile &&) = delete;

    FILE *get() {
        if (filep != nullptr) return filep;
        filep = fopen(logPath.c_str(), "w");
        if (filep == nullptr) {
            throw std::runtime_error("cannot open logfile");
        }
        return filep;
    }

    ~LogFile() {
        if (filep != nullptr) {
            fclose(filep);
            filep = nullptr;
        }
    }

  private:
    FILE *filep = nullptr;
    std::string logPath;
};

JNIEXPORT void JNICALL
Java_org_openobservatory_measurement_1kit_jni_sync_OoniSyncApi_dnsInjection(
    JNIEnv *env, jclass /*clazz*/, jstring backend, jstring inputPath,
    jstring reportPath, jstring logPath, jboolean verbose) {
    try {
        LogFile log_file(env, logPath);
        mk::ooni::DnsInjectionTest()
            .set_options("backend", mk::jni::cxxstring(env, backend))
            .set_input_file_path(mk::jni::cxxstring(env, inputPath))
            .set_output_file_path(mk::jni::cxxstring(env, reportPath))
            .set_verbosity(verbose)
            .on_log([&log_file](uint32_t, const char *s) {
                __android_log_print(ANDROID_LOG_INFO,
                        "dns-injection", "%s", s);
                fprintf(log_file.get(), "%s\n", s);
            })
            .run();
    } catch (...) {
        // XXX suppress
    }
}

JNIEXPORT void JNICALL
Java_org_openobservatory_measurement_1kit_jni_sync_OoniSyncApi_httpInvalidRequestLine(
    JNIEnv *env, jclass /*clazz*/, jstring backend, jstring reportPath,
    jstring logPath, jboolean verbose) {
    try {
        LogFile log_file(env, logPath);
        mk::ooni::HttpInvalidRequestLineTest()
            .set_options("backend", mk::jni::cxxstring(env, backend))
            .set_output_file_path(mk::jni::cxxstring(env, reportPath))
            .set_verbosity(verbose)
            .on_log([&log_file](uint32_t, const char *s) {
                __android_log_print(ANDROID_LOG_INFO,
                        "http-invalid-request-line", "%s", s);
                fprintf(log_file.get(), "%s\n", s);
            })
            .run();
    } catch (...) {
        // XXX suppress
    }
}

JNIEXPORT void JNICALL
Java_org_openobservatory_measurement_1kit_jni_sync_OoniSyncApi_tcpConnect(
    JNIEnv *env, jclass /*clazz*/, jstring port, jstring inputPath,
    jstring reportPath, jstring logPath, jboolean verbose) {
    try {
        LogFile log_file(env, logPath);
        mk::ooni::TcpConnectTest()
            .set_options("port", mk::jni::cxxstring(env, port))
            .set_input_file_path(mk::jni::cxxstring(env, inputPath))
            .set_output_file_path(mk::jni::cxxstring(env, reportPath))
            .set_verbosity(verbose)
            .on_log([&log_file](uint32_t, const char *s) {
                __android_log_print(ANDROID_LOG_INFO,
                        "tcp-connect", "%s", s);
                fprintf(log_file.get(), "%s\n", s);
            })
            .run();
    } catch (...) {
        // XXX suppress
    }
}
