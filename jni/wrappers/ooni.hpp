// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
// =============================================================
// Based on <https://stackoverflow.com/questions/7776800>,
//          <https://stackoverflow.com/questions/24403340>
#ifndef MK_JNI_WRAPPERS_ORCHESTRATE_HPP
#define MK_JNI_WRAPPERS_ORCHESTRATE_HPP

#include "environment_.hpp"
#include <jni.h>
#include <measurement_kit/ooni.hpp>

#ifdef __ANDROID__
#include <android/log.h>
#endif

typedef jobject mk_array_list_strings_t;
typedef jobject mk_error_t;
typedef jobject mk_list_strings_t;

typedef jobject mk_orchestrate_register_probe_callback_t;
typedef jobject mk_orchestrate_find_location_callback_t;
typedef jobject mk_orchestrate_update_callback_t;

#ifndef SWIG

/// Make o/o/m/j/c/Error class from mk::Error.
/// \param environ Java environment pointer wrapper.
/// \param error The mk::Error.
/// \return Reference to o/o/m/j/c/Error class.
static jobject make_error(Environment &environ, mk::Error &&error) {
    auto clazz = environ.find_class(
        "org/openobservatory/measurement_kit/swig/Error");
    auto mid = environ.get_method_id(clazz, "<init>", "()V");
    auto java_error = environ.new_object(clazz, mid);
    mid = environ.get_method_id(clazz, "set_code", "(I)V");
    environ.call_void_method(java_error, mid, error.code);
    auto s = environ.new_string_utf(error.reason.c_str());
    mid = environ.get_method_id(clazz, "set_reason", "(Ljava/lang/String;)V");
    environ.call_void_method(java_error, mid, s);
    return java_error;
}

/// Make o/o/m/j/o/OrchestrateAuth class from mk::ooni::orchestrate::Auth.
/// \param environ Java environment pointer wrapper.
/// \param auth The mk::ooni::orchestrate::Auth.
/// \return Reference to o/o/m/j/o/OrchestrateAuth.
static jobject make_auth(Environment &environ,
                         mk::ooni::orchestrate::Auth &&auth) {
    auto clazz = environ.find_class(
        "org/openobservatory/measurement_kit/swig/OrchestrateAuth");
    auto mid = environ.get_method_id(clazz, "<init>", "()V");
    auto java_auth = environ.new_object(clazz, mid);
#define XX(_property_)                                                         \
    do {                                                                       \
        auto mid = environ.get_method_id(clazz, "set_" #_property_,            \
                                         "(Ljava/lang/String;)V");             \
        auto string = environ.new_string_utf(auth._property_.c_str());         \
        environ.call_void_method(java_auth, mid, string);                      \
    } while (0)
    XX(auth_token);
    XX(expiry_time);
    XX(username);
    XX(password);
#undef XX
    mid = environ.get_method_id(clazz, "set_logged_in", "(Z)V");
    environ.call_void_method(java_auth, mid, auth.logged_in);
    return java_auth;
}

#endif // SWIG

class OrchestrateAuth {
  public:
    OrchestrateAuth() {}

    std::string auth_token() { return auth_.auth_token; }

    void set_auth_token(std::string s) { auth_.auth_token = s; }

    std::string expiry_time() { return auth_.expiry_time; }

    void set_expiry_time(std::string s) { auth_.expiry_time = s; }

    bool logged_in() { return auth_.logged_in; }

    void set_logged_in(bool b) { auth_.logged_in = b; }

    std::string username() { return auth_.username; }

    void set_username(std::string s) { auth_.username = s; }

    std::string password() { return auth_.password; }

    void set_password(std::string s) { auth_.password = s; }

    static std::string make_password() {
        return mk::ooni::orchestrate::Auth::make_password();
    }

    mk_error_t load(std::string s) {
        Environment environ;
        return environ.trap_route_exceptions_and_return(
            [&]() { return make_error(environ, auth_.load(s)); });
    }

    mk_error_t loads(std::string s) {
        Environment environ;
        return environ.trap_route_exceptions_and_return(
            [&]() { return make_error(environ, auth_.loads(s)); });
    }

    mk_error_t dump(std::string s) {
        Environment environ;
        return environ.trap_route_exceptions_and_return(
            [&]() { return make_error(environ, auth_.dump(s)); });
    }

    std::string dumps() { return auth_.dumps(); }

    // TODO: not clear to me how to extract the `Var<Logger>` from a SWIG
    // wrapper object that is a proxy for such logger. So, here I've removed
    // the `logger` argument and I'm passing a custom logger instead.
    bool is_valid() {
        auto logger = mk::Logger::make();
        return auth_.is_valid(logger);
    }

#ifndef SWIG
    mk::ooni::orchestrate::Auth auth_;
#endif
};

class OrchestrateClient {
  public:
    OrchestrateClient() {}

    // TODO: not clear to me how to extract the `Var<Logger>` from a SWIG
    // wrapper object that is a proxy for such logger. So, rather than having
    // a logger attribute, here I've added methods to configure the logger.

    void set_verbosity(uint32_t verbosity) {
        client_.logger->set_verbosity(verbosity);
    }

    void increase_verbosity() { client_.logger->increase_verbosity(); }

    void use_logcat() {
#ifdef __ANDROID__
        client_.logger->on_log([](uint32_t level, const char *msg) {
            level &= MK_LOG_VERBOSITY_MASK;
            if (level <= MK_LOG_WARNING) {
                __android_log_print(ANDROID_LOG_WARN, "MK", "%s", msg);
            } else if (level <= MK_LOG_INFO) {
                __android_log_print(ANDROID_LOG_INFO, "MK", "%s", msg);
            } else {
                __android_log_print(ANDROID_LOG_DEBUG, "MK", "%s", msg);
            }
        });
#endif
    }

    void set_logfile(std::string logfile) {
        client_.logger->set_logfile(logfile);
    }

    std::string available_bandwidth() { return client_.available_bandwidth; }

    void set_available_bandwidth(std::string s) {
        client_.available_bandwidth = s;
    }

    std::string device_token() { return client_.device_token; }

    void set_device_token(std::string s) { client_.device_token = s; }

    std::string events_url() { return client_.events_url; }

    void set_events_url(std::string s) { client_.events_url = s; }

    std::string language() { return client_.language; }

    void set_language(std::string s) { client_.language = s; }

    std::string network_type() { return client_.network_type; }

    void set_network_type(std::string s) { client_.network_type = s; }

    std::string geoip_country_path() { return client_.geoip_country_path; }

    void set_geoip_country_path(std::string s) {
        client_.geoip_country_path = s;
    }

    std::string geoip_asn_path() { return client_.geoip_asn_path; }

    void set_geoip_asn_path(std::string s) { client_.geoip_asn_path = s; }

    std::string platform() { return client_.platform; }

    void set_platform(std::string s) { client_.platform = s; }

    std::string probe_asn() { return client_.probe_asn; }

    void set_probe_asn(std::string s) { client_.probe_asn = s; }

    std::string probe_cc() { return client_.probe_cc; }

    void set_probe_cc(std::string s) { client_.probe_cc = s; }

    std::string probe_family() { return client_.probe_family; }

    void set_probe_family(std::string s) { client_.probe_family = s; }

    std::string registry_url() { return client_.registry_url; }

    void set_registry_url(std::string s) { client_.registry_url = s; }

    std::string software_name() { return client_.software_name; }

    void set_software_name(std::string s) { client_.software_name = s; }

    std::string software_version() { return client_.software_version; }

    void set_software_version(std::string s) { client_.software_version = s; }

    mk_array_list_strings_t supported_tests() {
        Environment environ;
        return environ.trap_route_exceptions_and_return([&environ, this]() {
            auto clazz = environ.find_class("java/util/ArrayList");
            auto mid = environ.get_method_id(clazz, "<init>", "()V");
            auto list = environ.new_object(clazz, mid);
            for (auto &s : client_.supported_tests) {
                auto copy = environ.new_string_utf(s.c_str());
                mid = environ.get_method_id(clazz, "add",
                        "(Ljava/lang/Object;)Z");
                environ.call_void_method(list, mid, copy);
            }
            return list;
        });
    }

    void set_supported_tests(mk_list_strings_t list) {
        Environment environ;
        environ.trap_and_route_exceptions([&environ, &list, this]() {
            auto list_class = environ.get_object_class(list);
            auto iter_mid = environ.get_method_id(list_class, "iterator",
                                                  "()Ljava/util/Iterator;");
            auto iter = environ.call_object_method(list, iter_mid);
            auto iter_class = environ.get_object_class(iter);
            auto next_mid = environ.get_method_id(iter_class, "next",
                                                  "()Ljava/lang/Object;");
            auto has_next_mid = environ.get_method_id(iter_class,
                                                  "hasNext", "()Z");
            std::vector<std::string> new_supported_tests;
            while (environ.call_boolean_method(iter, has_next_mid)) {
                auto elem = environ.call_object_method(iter, next_mid);
                environ.with_string_utf_chars((jstring) elem,
                    [&new_supported_tests](const char *s) {
                        new_supported_tests.push_back(s);
                    });
            }
            client_.supported_tests = new_supported_tests;
        });
    }

    void register_probe(std::string password,
                        mk_orchestrate_register_probe_callback_t cb) {
        Environment environ;
        environ.trap_and_route_exceptions([&environ, &cb, &password, this]() {
            jobject global_cb = environ.new_global_ref(cb);
            // Note: `register_probe()` will not throw exceptions, thus we can
            // be confident that we're not going to leak `global_cb`
            client_.register_probe(
                std::move(password),
                [global_cb](mk::Error &&error,
                            mk::ooni::orchestrate::Auth &&auth) {
                    Environment environ;
                    // Note: when this environment exits, we don't need
                    // anymore to prevent destruction of `global_cb`
                    environ.own_global(global_cb);
                    environ.trap_and_route_exceptions([&]() {
                        // Note: we own both `java_error` and `java_auth` but
                        // of course the callback can reference them and so keep
                        // them alive potentially forver.
                        auto java_error = make_error(environ, std::move(error));
                        auto java_auth = make_auth(environ, std::move(auth));
                        auto clazz = environ.get_object_class(global_cb);
                        auto mid = environ.get_method_id(
                            clazz, "callback",
                            "(Lorg/openobservatory/measurement_kit/swig/Error;"
                            "Lorg/openobservatory/measurement_kit/swig/"
                            "OrchestrateAuth;)V");
                        environ.call_void_method(global_cb, mid, java_error,
                                                 java_auth);
                    });
                });
        });
    }

    void find_location(mk_orchestrate_find_location_callback_t cb) {
        Environment environ;
        environ.trap_and_route_exceptions([&environ, &cb, this]() {
            jobject global_cb = environ.new_global_ref(cb);
            // Note: `find_location()` will not throw exceptions, thus we can
            // be confident that we're not going to leak `global_cb`
            client_.find_location([global_cb](mk::Error &&error,
                                              std::string &&probe_asn,
                                              std::string &&probe_cc) {
                Environment environ;
                // Note: when this environment exits, we don't need
                // anymore to prevent destruction of `global_cb`
                environ.own_global(global_cb);
                environ.trap_and_route_exceptions([&]() {
                    // Note: we own all the variables passed to callback;
                    // of course the callback can reference them and so keep
                    // them alive potentially forver.
                    auto java_error = make_error(environ, std::move(error));
                    auto java_probe_asn =
                        environ.new_string_utf(probe_asn.c_str());
                    auto java_probe_cc =
                        environ.new_string_utf(probe_cc.c_str());
                    auto clazz = environ.get_object_class(global_cb);
                    auto mid = environ.get_method_id(
                        clazz, "callback",
                        "(Lorg/openobservatory/measurement_kit/swig/Error;"
                        "Ljava/lang/String;Ljava/lang/String;)V");
                    environ.call_void_method(global_cb, mid, java_error,
                                             java_probe_asn, java_probe_cc);
                });
            });
        });
    }

    void update(OrchestrateAuth auth, mk_orchestrate_update_callback_t cb) {
        Environment environ;
        environ.trap_and_route_exceptions([&environ, &auth, &cb, this]() {
            jobject global_cb = environ.new_global_ref(cb);
            // Note: `update()` will not throw exceptions, thus we can
            // be confident that we're not going to leak `global_cb`
            client_.update(
                std::move(auth.auth_),
                [global_cb](mk::Error &&error,
                            mk::ooni::orchestrate::Auth &&auth) {
                    Environment environ;
                    // Note: when this environment exits, we don't need anymore
                    // to prevent destruction of `global_cb`
                    environ.own_global(global_cb);
                    environ.trap_and_route_exceptions([&]() {
                        // Note: we own both `java_error` and `java_auth` but of
                        // course the callback can reference them and so keep
                        // them alive potentially forver.
                        auto java_error = make_error(environ, std::move(error));
                        auto java_auth = make_auth(environ, std::move(auth));
                        auto clazz = environ.get_object_class(global_cb);
                        auto mid = environ.get_method_id(
                            clazz, "callback",
                            "(Lorg/openobservatory/measurement_kit/swig/Error;"
                            "Lorg/openobservatory/measurement_kit/swig/"
                            "OrchestrateAuth;)V");
                        environ.call_void_method(global_cb, mid, java_error,
                                                 java_auth);
                    });
                });
        });
    }

#ifndef SWIG
    mk::ooni::orchestrate::Client client_;
#endif
};

#endif
