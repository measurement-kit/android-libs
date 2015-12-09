// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include <jni.h>
#include <limits.h>
#include <measurement_kit/net.hpp>
#include <measurement_kit/traceroute.hpp>
#include <string>
#include "common.hpp"
#include "io_github_measurement_kit_jni_sync_PortolanSyncApi.h"

using namespace mk;
using namespace mk::traceroute;

class ProberContext {
  public:
    Poller poller;
    AndroidProber prober;

    /// Constructor with @use4 and @s_port
    ProberContext(bool use4, int s_port) : prober(
            use4, s_port, poller.get_event_base()) {}
};

static const char *map_code(ProbeResult r) {

#define XX(kkk) \
    if (meaning == ProbeResultMeaning::kkk) \
        return #kkk

    try {
        ProbeResultMeaning meaning = r.get_meaning();
        XX(NO_ROUTE_TO_HOST);
        XX(ADDRESS_UNREACH);
        XX(PROTO_NOT_IMPL);
        XX(PORT_IS_CLOSED);
        XX(TTL_EXCEEDED);
        XX(ADMIN_FILTER);
        XX(GOT_REPLY_PACKET);
    } catch (...) {
        // fallthrough...
    }

#undef XX

    return "OTHER";
}

JNIEXPORT jlong JNICALL
Java_io_github_measurement_1kit_jni_sync_PortolanSyncApi_openProber
  (JNIEnv * /*env*/, jclass /*clazz*/, jboolean use_ipv4, jint port) {
    try {
        return (jlong) new ProberContext(use_ipv4, port);
    } catch (...) {
        return (jlong) 0L;
    }
}

JNIEXPORT void JNICALL
Java_io_github_measurement_1kit_jni_sync_PortolanSyncApi_sendProbe
  (JNIEnv *env, jclass /*clazz*/, jlong ptr, jstring destIp, jint destPort,
   jint ttl, jdouble timeout, jobjectArray outStrings, jintArray outInts,
   jdoubleArray outDoubles) {

    static const int payload_size = 256;

    ProbeResult result;
    try {
        if (ptr == 0L) throw std::runtime_error("Null pointer");
        ProberContext *ctx = (ProberContext *) ptr;
        std::string payload(payload_size, '\0');
        ctx->prober.on_result([&ctx, &result](ProbeResult r) {
            result = r;
            ctx->poller.break_loop();
        });
        ctx->prober.on_timeout([&ctx]() { ctx->poller.break_loop(); });
        ctx->prober.on_error([&ctx](Error) { ctx->poller.break_loop(); });
        ctx->poller.loop();
    } catch (...) {
        // fallthrough; by default `result` contains an error
    }

    jstring temp;

    // Note: the map_code() function provides a no-throw guarantee
    temp = env->NewStringUTF(map_code(result));
    if (!temp) return;
    env->SetObjectArrayElement(outStrings, 0, temp);
    if (env->ExceptionCheck()) return;

    // Note: the c_str() function provides a no-throw guarantee
    temp = env->NewStringUTF(result.interface_ip.c_str());
    if (!temp) return;
    env->SetObjectArrayElement(outStrings, 1, temp);
    if (env->ExceptionCheck()) return;

    if (result.reply.length() > INT_MAX) return; // for static_cast
    const static size_t int_array_size = 3;
    jint int_array[int_array_size] = {
            result.ttl,
            static_cast<int>(result.reply.length()),
            result.is_ipv4
    };
    env->SetIntArrayRegion(outInts, 0, int_array_size, int_array);
    if (env->ExceptionCheck()) return;

    const static size_t double_array_size = 1;
    jdouble double_array[double_array_size] = { result.rtt };
    env->SetDoubleArrayRegion(outDoubles, 0, double_array_size, double_array);
    if (env->ExceptionCheck()) return;
}

JNIEXPORT void JNICALL
Java_io_github_measurement_1kit_jni_sync_PortolanSyncApi_closeProber
  (JNIEnv * /*env*/, jclass /*clazz*/, jlong ptr) {
    // Note: `delete` gracefully handles null pointers
    try {
        ProberContext *ctx = (ProberContext *) ptr;
        delete ctx;
    } catch (...) {
        // XXX suppress
    }
}

JNIEXPORT jboolean JNICALL
Java_io_github_measurement_1kit_jni_sync_PortolanSyncApi_checkPort
       (JNIEnv *env, jclass /*clazz*/, jboolean use_ipv4, jstring address,
        jstring port, jdouble timeout) {
    jboolean is_port_open = JNI_FALSE;
    try {
        Poller poller;
        net::Transport connection = net::connect({
            {"family", (use_ipv4) ? "PF_INET" : "PF_INET6"},
            {"address", mk::jni::cxxstring(env, address)},
            {"port", mk::jni::cxxstring(env, port)},
        }, Logger::global(), &poller).as_value();
        connection.set_timeout(timeout);
        connection.on_error([&poller](Error) {
            poller.break_loop();
        });
        connection.on_connect([&poller, &is_port_open]() {
            is_port_open = JNI_TRUE;
            poller.break_loop();
        });
        poller.loop();
    } catch (...) {
        // XXX suppress
    }
    return is_port_open;
}
