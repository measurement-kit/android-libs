// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
#ifndef JNI_OONI_TEST_WRAPPER_HPP
#define JNI_OONI_TEST_WRAPPER_HPP

#include <measurement_kit/ooni.hpp>
#include <string>

class OoniTestWrapper {
  public:
    OoniTestWrapper(std::string test_name) {
        if (test_name == "tcp_connect") {
            real_test_.reset(new mk::ooni::TcpConnect);
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
        // XXX: this requires MK v0.3.0
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
