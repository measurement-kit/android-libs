// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
#ifndef MK_JNI_WRAPPERS_COMMON_HPP
#define MK_JNI_WRAPPERS_COMMON_HPP

#include <measurement_kit/common/error.hpp>
#include <measurement_kit/common/version.h>

#ifdef __ANDROID__
#include <android/log.h>
#endif

class Error {
  public:
#ifndef SWIG
    Error(mk::Error error) : error_{error} {}
#endif

    Error() {}

    bool as_bool() { return static_cast<bool>(error_); }

    int code() { return error_.code; }

    void set_code(int code) { error_.code = code; }

    std::string reason() { return error_.reason; }

    void set_reason(std::string r) { error_.reason = r; }

#ifndef SWIG
    mk::Error error_;
#endif
};

class Version {
  public:
    static std::string version() { return mk_version(); }
  private:
    Version() {} // So swig does not generate a Java constructor
};

#endif
