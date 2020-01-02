// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
#ifndef MEASUREMENT_KIT_MKCOLLECTOR_HPP
#define MEASUREMENT_KIT_MKCOLLECTOR_HPP

#include <stdint.h>

#include <initializer_list>
#include <string>
#include <vector>

#include "mkcurl.hpp"

/// MKCOLLECTOR_INLINE_NAMESPACE controls the inline inner namespace in which
/// public symbols exported by this library are enclosed.
///
/// See <https://github.com/measurement-kit/measurement-kit/issues/1867#issuecomment-514562622>.
#define MKCOLLECTOR_INLINE_NAMESPACE v0_7_0_or_greater

namespace mk {
namespace collector {
inline namespace MKCOLLECTOR_INLINE_NAMESPACE {

/// Settings contains common network related settings.
class Settings {
 public:
  /// base_url is the OONI collector base_url
  std::string base_url;

  /// ca_bundle_path is the path to the CA bundle (required on mobile)
  std::string ca_bundle_path;

  /// timeout is the whole operation timeout (in seconds). Zero indicatest
  /// that there actually is no timeout.
  int64_t timeout = 0;
};

/// LoadResult is the result of loading a structure from JSON.
template <typename Type>
class LoadResult {
 public:
  /// good indicates whether loading succeeded.
  bool good = false;

  /// reason indicates the failure reason on failure.
  std::string reason;

  /// value is the parsed value on success.
  Type value = {};
};

/// OpenRequest is a request to open a report with a collector.
class OpenRequest {
 public:
  /// operator!= returns true if this object differs from @p other
  bool operator!=(const OpenRequest &other) const noexcept;

#define MKCOLLECTOR_OPEN_REQUEST_ENUM(XX) \
  XX(probe_asn)                           \
  XX(probe_cc)                            \
  XX(software_name)                       \
  XX(software_version)                    \
  XX(test_name)                           \
  XX(test_start_time)                     \
  XX(test_version)

#define XX(name_) std::string name_;
  MKCOLLECTOR_OPEN_REQUEST_ENUM(XX)
#undef XX
};

/// open_request_from_measurement initializes an OpenRequest structure
/// from an existing @p measurement. This factory also requires you
/// to pass @p software_name and @p software_version to inform the OONI
/// collector about who is submitting this measurement. This is the function
/// that you want to call when you want to resubmit a measurement.
LoadResult<OpenRequest> open_request_from_measurement(
    const std::string &measurement, const std::string &software_name,
    const std::string &software_version) noexcept;

/// OpenResponse is the response to an open request.
struct OpenResponse {
  /// good indicates whether we succeded.
  bool good = false;

  /// reason is the reason of failure.
  std::string reason;

  /// report_id is the report ID (only meaningful on success).
  std::string report_id;

  /// logs contains the logs.
  std::vector<std::string> logs;
};

/// open opens a report with a collector.
OpenResponse open(const OpenRequest &request,
                  const Settings &settings) noexcept;

/// UpdateRequest is a request to update a report with a new measurement.
struct UpdateRequest {
  /// report_id is the report ID.
  std::string report_id;

  /// content is the measurement entry serialised as a string.
  std::string content;
};

/// UpdateResponse is a response to an update request.
struct UpdateResponse {
  /// good indicates whether we succeded.
  bool good = false;

  /// reason is the reason of failure.
  std::string reason;

  /// logs contains the logs.
  std::vector<std::string> logs;
};

/// update updates a report by adding a new measurement.
UpdateResponse update(const UpdateRequest &request,
                      const Settings &settings) noexcept;

/// CloseRequest is a request to close a report.
struct CloseRequest {
  /// report_id is the report ID
  std::string report_id;
};

/// CloseResponse is a response to a close request
struct CloseResponse {
  /// good indicates whether the operation succeeded
  bool good = false;

  /// reason is the reason of failure.
  std::string reason;

  /// logs contains the logs.
  std::vector<std::string> logs;
};

/// close closes a report.
CloseResponse close(const CloseRequest &request,
                    const Settings &settings) noexcept;

/// Reporter submits measurements as part of the same report.
///
/// This class must not be shared among threads. That's why we enforce
/// unique ownership semantic by cancelling copy operations.
class Reporter {
 public:
  /// Reporter constructs a new reporter using the specified @p software_name
  /// as the name of the tool that is submitting, and with the version of such
  /// tool in @p software_version.
  Reporter(std::string software_name, std::string software_version) noexcept;

  /// Reporter is the deleted copy constructor.
  Reporter(const Reporter &) noexcept = delete;

  /// Reporter is the deleted copy assignment.
  Reporter &operator=(const Reporter &) noexcept = delete;

  /// Reporter is the move constructor.
  Reporter(Reporter &&) noexcept = default;

  /// Reporter is the move assignment.
  Reporter &operator=(Reporter &&) noexcept = default;

  /// set_ca_bundle_path sets the optional CA bundle path.
  void set_ca_bundle_path(std::string path) noexcept;

  /// ca_bundle_path returns the currently configured CA bundle path.
  const std::string &ca_bundle_path() const noexcept;

  /// set_base_url sets the collector base URL. If not set (the default) we
  /// will use the bouncer API to discover this URL.
  void set_base_url(std::string url) noexcept;

  /// base_url returns the currently set collector base URL.
  const std::string &base_url() const noexcept;

  /*
   * Testing helpers. Allow you to know about what code paths were
   * takens. They can change at any time.
   */

#define MKCOLLECTOR_REPORTER_STATS_ENUM(XX) \
  XX(bouncer_error)                         \
  XX(bouncer_okay)                          \
  XX(bouncer_no_collectors)                 \
  XX(load_request_error)                    \
  XX(load_request_okay)                     \
  XX(close_report_error)                    \
  XX(close_report_okay)                     \
  XX(open_report_error)                     \
  XX(report_id_empty)                       \
  XX(open_report_okay)                      \
  XX(serialize_measurement_error)           \
  XX(update_report_error)                   \
  XX(update_report_okay)

  // Stats contains stats about a submission.
  struct Stats {
    // operator== compares this with @p other for equality.
    bool operator==(const Stats &other) const;

    // Stats is the default constructor.
    Stats() noexcept;

    // Stats initializes this class from a list of strings indicating what
    // fields must be set to nonzero.
    explicit Stats(std::initializer_list<std::string> list) noexcept;

#define XX(name_) unsigned name_ = 0;
    MKCOLLECTOR_REPORTER_STATS_ENUM(XX)
#undef XX
  };

  /// maybe_discover_and_submit_with_stats_and_reason is like
  /// maybe_discover_and_submit_with_timeout but stores stats in @p stats
  /// and the reason in @p reason.
  bool maybe_discover_and_submit_with_stats_and_reason(
      std::string &measurement, std::vector<std::string> &logs,
      int64_t upload_timeout, Stats &stats,
      std::string &reason) noexcept;

  /// maybe_discover_and_submit_with_timeout is like submit but enforces @p
  /// upload_timeout as the / number of seconds after which the HTTP
  /// upload is aborted.
  bool maybe_discover_and_submit_with_timeout(
      std::string &measurement, std::vector<std::string> &logs,
      int64_t upload_timeout) noexcept;

  /// maybe_discover_and_submit submits @p measurement to the configured
  /// collector. If no collector is configured, this function will discover
  /// a suitable collector. The @p measurement string is modified in place to
  /// point to the correct report / ID as part of the submission. This
  /// function implements the following algorithm:
  ///
  /// 0. if no base_url_ is configured, the bouncer is used
  /// to discover the base URL that should be used;
  ///
  /// 1. the same HTTP client is used throughout the lifecycle of this
  /// class, hence existing HTTP connections are reused if possible;
  ///
  /// 2. the measurement is loaded as a JSON.
  ///
  /// 3. if we already openned a report and the current measurement is
  /// different (as defined below) from the previous measurement, then we
  /// close the current report;
  ///
  /// 4. if no report is open, then we open a report;
  ///
  /// 5. we submit the measurement as part of the current report;
  ///
  /// 6. we modify the measurement to update the report ID.
  ///
  /// A measurement is different from the previous measurement if the
  /// OpenRequest structure obtained from successfully loading the measurement
  /// using open_request_from_measurement is different from the previously
  /// cached structure obtained from a previous measurement.
  ///
  /// @return true on success and false on failure. Consult the @p logs
  /// argument for more information on what has happened.
  bool maybe_discover_and_submit(
      std::string &measurement, std::vector<std::string> &logs) noexcept;

  /// report_id contains the currently used report ID.
  const std::string &report_id() const noexcept;

  /// ~Reporter will close the report if necessary.
  ~Reporter() noexcept;

 private:
  // make_settings creates a setting structure with the specified @p timeout.
  Settings make_settings(int64_t timeout) const noexcept;

  // base_url_ contains the collector base URL.
  std::string base_url_;

  // ca_bundle_path_ is the CA bundle path to use.
  std::string ca_bundle_path_;

  // client_ is the mkcurl client to use.
  curl::Client client_;

  // cached_open_request_ is the latest cached open request used to
  // decide whether a measurement belongs to the current report or
  // whether we need to close this report and open a new one.
  OpenRequest cached_open_request_;

  // report_id_ is the report ID to use.
  std::string report_id_;

  // short_timeout is the API calls timeout in seconds.
  int64_t short_timeout_ = 30;

  // software_name_ is the name of the tool that is submitting.
  std::string software_name_;

  // software_version_ is the version of the tool that is submitting.
  std::string software_version_;
};

}  // inline namespace MKCOLLECTOR_INLINE_NAMESPACE
}  // namespace collector
}  // namespace mk

// The implementation can be included inline by defining this preprocessor
// symbol. If you only care about API, you can stop reading here.
#ifdef MKCOLLECTOR_INLINE_IMPL

#include <stdexcept>
#include <sstream>

#include <curl/curl.h>

#include "json.hpp"
#include "mkbouncer.hpp"
#include "mkmock.hpp"

#ifdef MKCOLLECTOR_MOCK
#define MKCOLLECTOR_HOOK MKMOCK_HOOK_ENABLED
#else
#define MKCOLLECTOR_HOOK MKMOCK_HOOK_DISABLED
#endif

namespace mk {
namespace collector {
inline namespace MKCOLLECTOR_INLINE_NAMESPACE {

// log_body is a helper to log about a body.
static void log_body(const std::string &prefix, const std::string &body,
                     std::vector<std::string> &logs) noexcept {
  std::stringstream ss;
  ss << prefix << " body: " << body;
  logs.push_back(ss.str());
}

bool OpenRequest::operator!=(const OpenRequest &other) const noexcept {
#define XX(name_) if (name_ != other.name_) return true;
  MKCOLLECTOR_OPEN_REQUEST_ENUM(XX)
#undef XX
  return false;
}

static LoadResult<OpenRequest> open_request_from_measurement_with_json_(
    const std::string &measurement, const std::string &software_name,
    const std::string &software_version, nlohmann::json &doc) noexcept {
  LoadResult<OpenRequest> result;
  try {
    doc = nlohmann::json::parse(measurement);
    doc.at("probe_asn").get_to(result.value.probe_asn);
    doc.at("probe_cc").get_to(result.value.probe_cc);
    doc.at("test_name").get_to(result.value.test_name);
    doc.at("test_start_time").get_to(result.value.test_start_time);
    doc.at("test_version").get_to(result.value.test_version);
  } catch (const std::exception &exc) {
    result.reason = exc.what();
    return result;
  }
  // We need to specify the software_name and software_version of the
  // app that is resubmitting; see https://github.com/ooni/spec/issues/134
  result.value.software_name = software_name;
  result.value.software_version = software_version;
  result.good = true;
  return result;
}

LoadResult<OpenRequest> open_request_from_measurement(
    const std::string &measurement, const std::string &software_name,
    const std::string &software_version) noexcept {
  nlohmann::json doc;
  return open_request_from_measurement_with_json_(
      measurement, software_name, software_version, doc
  );
}

// curl_reason_for_failure contains the cURL reason for failure.
static std::string curl_reason_for_failure(
    const curl::Response &response) noexcept {
  if (response.error != 0) {
    std::string rv = "collector: ";
    rv += curl_easy_strerror((CURLcode)response.error);
    return rv;
  }
  if (response.status_code != 200) {
    std::string rv = "collector: ";
    rv += curl_easy_strerror(CURLE_HTTP_RETURNED_ERROR);
    return rv;
  }
  return "collector: unknown libcurl error";
}

static OpenResponse open_with_client_(
    curl::Client &client, const OpenRequest &request,
    const Settings &settings) noexcept {
  OpenResponse response;
  curl::Request curl_request;
  curl_request.ca_path = settings.ca_bundle_path;
  curl_request.timeout = settings.timeout;
  curl_request.method = "POST";
  curl_request.headers.push_back("Content-Type: application/json");
  {
    std::string url = settings.base_url;
    url += "/report";
    std::swap(url, curl_request.url);
  }
  {
    std::string body;
    nlohmann::json doc;
    doc["data_format_version"] = "0.2.0";
    doc["format"] = "json";
    doc["input_hashes"] = nlohmann::json::array();
    doc["probe_asn"] = request.probe_asn;
    doc["probe_cc"] = request.probe_cc;
    doc["software_name"] = request.software_name;
    doc["software_version"] = request.software_version;
    doc["test_name"] = request.test_name;
    doc["test_start_time"] = request.test_start_time;
    doc["test_version"] = request.test_version;
    try {
      body = doc.dump();
    } catch (const std::exception &exc) {
      response.logs.push_back(exc.what());
      response.reason = exc.what();
      return response;
    }
    log_body("Request", body, response.logs);
    std::swap(body, curl_request.body);
  }
  curl::Response curl_response = client.perform(curl_request);
  for (auto &entry : curl_response.logs) {
    response.logs.push_back(std::move(entry.line));
  }
  MKCOLLECTOR_HOOK(open_response_error, curl_response.error);
  MKCOLLECTOR_HOOK(open_response_status_code, curl_response.status_code);
  if (curl_response.error != 0 || curl_response.status_code != 200) {
    response.reason = curl_reason_for_failure(curl_response);
    return response;
  }
  MKCOLLECTOR_HOOK(open_response_body, curl_response.body);
  {
    log_body("Response", curl_response.body, response.logs);
    nlohmann::json doc;
    try {
      doc = nlohmann::json::parse(curl_response.body);
      doc.at("report_id").get_to(response.report_id);
    } catch (const std::exception &exc) {
      response.logs.push_back(exc.what());
      response.reason = exc.what();
      return response;
    }
  }
  response.good = true;
  return response;
}

OpenResponse open(const OpenRequest &request,
                  const Settings &settings) noexcept {
  curl::Client client;
  return open_with_client_(client, request, settings);
}

static UpdateResponse update_with_client_(
    curl::Client &client, const UpdateRequest &request,
    const Settings &settings) noexcept {
  UpdateResponse response;
  curl::Request curl_request;
  curl_request.ca_path = settings.ca_bundle_path;
  curl_request.timeout = settings.timeout;
  curl_request.method = "POST";
  curl_request.headers.push_back("Content-Type: application/json");
  {
    std::string url = settings.base_url;
    url += "/report/";
    url += request.report_id;
    std::swap(url, curl_request.url);
  }
  {
    std::string body;
    nlohmann::json doc;
    doc["format"] = "json";
    try {
      auto content = nlohmann::json::parse(request.content);
      // Implementation note: the following checks rely on the fact that
      // we're inside a try...catch block and nlohmann/json will throw if
      // content is not an object, a field is missing, etc. That's also
      // why we're using throw to leave this block rather than return.
      if (content.at("data_format_version") != "0.2.0") {
        throw std::runtime_error("Unsupported data_format_version");
      }
      if (content.at("report_id") != request.report_id) {
        throw std::runtime_error("The report_id is inconsistent");
      }
      doc["content"] = std::move(content);
      body = doc.dump();
    } catch (const std::exception &exc) {
      response.logs.push_back(exc.what());
      response.reason = exc.what();
      return response;
    }
    log_body("Request", body, response.logs);
    std::swap(body, curl_request.body);
  }
  curl::Response curl_response = client.perform(curl_request);
  for (auto &entry : curl_response.logs) {
    response.logs.push_back(std::move(entry.line));
  }
  MKCOLLECTOR_HOOK(update_response_error, curl_response.error);
  MKCOLLECTOR_HOOK(update_response_status_code, curl_response.status_code);
  if (curl_response.error != 0 || curl_response.status_code != 200) {
    response.reason = curl_reason_for_failure(curl_response);
    return response;
  }
  log_body("Response", curl_response.body, response.logs);
  response.good = true;
  return response;
}

UpdateResponse update(const UpdateRequest &request,
                      const Settings &settings) noexcept {
  curl::Client client;
  return update_with_client_(client, request, settings);
}

static CloseResponse close_with_client_(
    curl::Client &client, const CloseRequest &request,
    const Settings &settings) noexcept {
  CloseResponse response;
  curl::Request curl_request;
  curl_request.method = "POST";
  curl_request.ca_path = settings.ca_bundle_path;
  curl_request.timeout = settings.timeout;
  {
    std::string url = settings.base_url;
    url += "/report/";
    url += request.report_id;
    url += "/close";
    std::swap(url, curl_request.url);
  }
  curl::Response curl_response = client.perform(curl_request);
  for (auto &entry : curl_response.logs) {
    response.logs.push_back(std::move(entry.line));
  }
  if (curl_response.error != 0 || curl_response.status_code != 200) {
    response.reason = curl_reason_for_failure(curl_response);
    return response;
  }
  log_body("Response", curl_response.body, response.logs);
  response.good = true;
  return response;
}

CloseResponse close(const CloseRequest &request,
                    const Settings &settings) noexcept {
  curl::Client client;
  return close_with_client_(client, request, settings);
}

Reporter::Reporter(
    std::string software_name, std::string software_version) noexcept {
  std::swap(software_version_, software_version);
  std::swap(software_name_, software_name);
}

void Reporter::set_ca_bundle_path(std::string path) noexcept {
  std::swap(path, ca_bundle_path_);
}

const std::string &Reporter::ca_bundle_path() const noexcept {
  return ca_bundle_path_;
}

void Reporter::set_base_url(std::string url) noexcept {
  std::swap(base_url_, url);
}

const std::string &Reporter::base_url() const noexcept {
  return base_url_;
}

bool Reporter::Stats::operator==(const Stats &other) const {
#define XX(name_) if (name_ != other.name_) return false;
  MKCOLLECTOR_REPORTER_STATS_ENUM(XX)
#undef XX
  return true;
}

Reporter::Stats::Stats() noexcept {}

Reporter::Stats::Stats(std::initializer_list<std::string> list) noexcept {
#define XX(name_)    \
  if (s == #name_) { \
    name_ = 1;       \
    continue;        \
  }
  for (auto &s : list) {
    MKCOLLECTOR_REPORTER_STATS_ENUM(XX)
  }
#undef XX
}

bool Reporter::maybe_discover_and_submit_with_stats_and_reason(
    std::string &measurement, std::vector<std::string> &logs,
    int64_t upload_timeout, Stats &stats, std::string &reason) noexcept {
  // step 0 (see description of the algorithm above) - maybe discover bouncer
  if (base_url_ == "") {
    // TODO(bassosimone): the bouncer API we're currently using only returns
    // a single collector, but a more modern API returns them all. We can maybe
    // change the bouncer client code to use the new API and then use that
    // here for robustness. Or, we can just switch to ooni/probe-engine that
    // already implements this functionality. Whatever happens first?
    logs.push_back("Using bouncer to discover a collector");
    mk::bouncer::Request request;
    request.ca_bundle_path = ca_bundle_path_;
    request.name = "web_connectivity";  // any test name is fine
    request.timeout = short_timeout_;
    request.version = "0.0.1";          // any version is fine
    mk::bouncer::Response response = mk::bouncer::perform(request);
    logs.insert(
        std::end(logs), std::begin(response.logs), std::end(response.logs));
    MKCOLLECTOR_HOOK(bouncer_response_good, response.good);
    if (!response.good) {
      reason = response.reason;
      stats.bouncer_error++;
      return false;
    }
    MKCOLLECTOR_HOOK(bouncer_response_collectors, response.collectors);
    for (auto &entry : response.collectors) {
      if (entry.type == "https") {
        base_url_ = entry.address;
        break;
      }
    }
    if (base_url_ == "") {
      const char *r = "No suitable collector found in bouncer response";
      logs.push_back(r);
      reason = r;
      stats.bouncer_no_collectors++;
      return false;
    }
    stats.bouncer_okay += 1;
    std::stringstream ss;
    ss << "Found this collector: " << base_url_;
    logs.push_back(ss.str());
    // FALLTHROUGH
  }
  // step 1 - use same HTTP client. Implied by using `client_` for any
  // collector operation throughout this function.
  UpdateRequest update_request;
  {
    nlohmann::json json_measurement;
    {
      OpenRequest open_request;
      {
        // step 2 - load measurement
        logs.push_back("Loading the measurement from JSON");
        auto load_result = open_request_from_measurement_with_json_(
            std::move(measurement),  // measurement becomes empty
            software_name_, software_version_, json_measurement);
        if (!load_result.good) {
          logs.push_back(std::move(load_result.reason));
          stats.load_request_error += 1;
          reason = std::move(load_result.reason);
          return false;
        }
        stats.load_request_okay += 1;
        open_request = std::move(load_result.value);
      }
      // step 3 - is this part of a previous report (if any)?
      if (report_id_ != "" && (open_request != cached_open_request_)) {
        logs.push_back("Closing previously open report");
        CloseRequest close_request;
        close_request.report_id = std::move(report_id_);  // clears report_id_
        auto close_response = close_with_client_(
            client_, close_request, make_settings(short_timeout_));
        logs.insert(std::end(logs), std::begin(close_response.logs),
                    std::end(close_response.logs));
        MKCOLLECTOR_HOOK(reporter_close_response_good, close_response.good);
        // DESIGN CHOICE: it's fine if we cannot close a report - keep going
        if (!close_response.good) {
          stats.close_report_error += 1;
          reason = std::move(close_response.reason);
        } else {
          stats.close_report_okay += 1;
        }
      }
      // step 4 - do we need to open a new report?
      if (report_id_ == "") {
        logs.push_back("Opening new report");
        auto open_response = open_with_client_(
            client_, open_request, make_settings(short_timeout_));
        logs.insert(std::end(logs), std::begin(open_response.logs),
                    std::end(open_response.logs));
        MKCOLLECTOR_HOOK(reporter_open_response_good, open_response.good);
        if (!open_response.good) {
          stats.open_report_error += 1;
          reason = std::move(open_response.reason);
          return false;
        }
        MKCOLLECTOR_HOOK(
            reporter_open_response_report_id, open_response.report_id);
        if (open_response.report_id == "") {
          const char *r = "Server returned an empty report ID";
          logs.push_back(r);
          reason = r;
          stats.report_id_empty += 1;
          return false;
        }
        stats.open_report_okay += 1;
        cached_open_request_ = std::move(open_request);  // open_request now empty
        report_id_ = std::move(open_response.report_id);
      }
    }
    // step 5 - prepare and submit measurement
    logs.push_back("Reformatting the measurement");
    update_request.report_id = report_id_;       // copy
    json_measurement["report_id"] = report_id_;  // copy
    try {
      update_request.content = json_measurement.dump();
    } catch (const std::exception &exc) {
      // Note: this seems extremely unlikely because the original measurement
      // was loaded from JSON and the report ID also was received as JSON, yet
      // we catch the exception nonetheless for ${robustness}.
      stats.serialize_measurement_error += 1;
      logs.push_back(exc.what());
      reason = exc.what();
      return false;
    }
  }
  logs.push_back("Updating the report");
  auto update_response = update_with_client_(
      client_, update_request, make_settings(upload_timeout));
  logs.insert(std::end(logs), std::begin(update_response.logs),
              std::end(update_response.logs));
  MKCOLLECTOR_HOOK(reporter_update_response_good, update_response.good);
  if (!update_response.good) {
    stats.update_report_error += 1;
    reason = std::move(update_response.reason);
    return false;
  }
  // step 6 - modify measurement to refer to the correct report ID
  measurement = std::move(update_request.content);
  stats.update_report_okay += 1;
  logs.push_back("Submission succeded");
  return true;
}

bool Reporter::maybe_discover_and_submit_with_timeout(
    std::string &measurement, std::vector<std::string> &logs,
    int64_t upload_timeout) noexcept {
  std::string reason;
  Stats stats;
  return maybe_discover_and_submit_with_stats_and_reason(
      measurement, logs, upload_timeout, stats, reason);
}

bool Reporter::maybe_discover_and_submit(
    std::string &measurement, std::vector<std::string> &logs) noexcept {
  return maybe_discover_and_submit_with_timeout(measurement, logs, 0);
}

const std::string &Reporter::report_id() const noexcept {
  return report_id_;
}

Reporter::~Reporter() noexcept {
  if (report_id_ != "") {
    CloseRequest close_request;
    close_request.report_id = std::move(report_id_);  // clear report ID
    (void)close_with_client_(
        client_, close_request, make_settings(short_timeout_));
  }
}

Settings Reporter::make_settings(int64_t timeout) const noexcept {
  Settings settings;
  settings.base_url = base_url_;
  settings.ca_bundle_path = ca_bundle_path_;
  settings.timeout = timeout;
  return settings;
}

}  // inline namespace MKCOLLECTOR_INLINE_NAMESPACE
}  // namespace collector
}  // namespace mk
#endif  // MKCOLLECTOR_INLINE_IMPL
#endif  // MEASUREMENT_KIT_MKCOLLECTOR_HPP
