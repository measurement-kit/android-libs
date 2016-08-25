// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
#ifndef JNI_OONI_TEST_WRAPPER_HPP
#define JNI_OONI_TEST_WRAPPER_HPP

#include <android/log.h>
#include <measurement_kit/ndt.hpp>
#include <measurement_kit/ooni.hpp>
#include <string>

class OoniTestWrapper {
  public:
    OoniTestWrapper(std::string test_name) {
        if (test_name == "dns_injection") {
            real_test_.reset(new mk::ooni::DnsInjection);
        } else if (test_name == "http_invalid_request_line") {
            real_test_.reset(new mk::ooni::HttpInvalidRequestLine);
        } else if (test_name == "ndt") {
            real_test_.reset(new mk::ndt::NdtTest);
        } else if (test_name == "tcp_connect") {
            real_test_.reset(new mk::ooni::TcpConnect);
        } else if (test_name == "web_connectivity") {
            real_test_.reset(new mk::ooni::WebConnectivity);
        } else {
            throw std::runtime_error("invalid test name");
        }
    };

    void set_verbosity(uint32_t verbosity) {
        real_test_->set_verbosity(verbosity);
    }

    void increase_verbosity() {
        real_test_->increase_verbosity();
    }

    void set_input_filepath(std::string fpath) {
        real_test_->set_input_filepath(fpath);
    }

    void set_output_filepath(std::string fpath) {
        real_test_->set_output_filepath(fpath);
    }

    void set_error_filepath(std::string fpath) {
        // XXX not available in v0.3.0-alpha
    }

    void use_logcat() {
        real_test_->on_log([](uint32_t level, const char *msg) {
            level &= MK_LOG_VERBOSITY_MASK;
            if (level <= MK_LOG_WARNING) {
                __android_log_print(ANDROID_LOG_WARN, "ooni", "%s", msg);
            } else if (level <= MK_LOG_INFO) {
                __android_log_print(ANDROID_LOG_INFO, "ooni", "%s", msg);
            } else {
                __android_log_print(ANDROID_LOG_DEBUG, "ooni", "%s", msg);
            }
        });
    }

    void set_options(std::string key, std::string value) {
        real_test_->set_options(key, value);
    }

    void run() {
        real_test_->run();
    }

  private:
    mk::Var<mk::NetTest> real_test_;
};

#endif
