// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "io_ooni_mk_MKReporterResults.h"
#include "io_ooni_mk_MKReporterTask.h"

#include <memory>
#include <string>
#include <vector>

#include "vendor/json.hpp"
#include "vendor/mkcollector.hpp"

#include "mkall_util.h"

JNIEXPORT jlong JNICALL Java_io_ooni_mk_MKReporterTask_New(
    JNIEnv *env, jclass /*clazz*/, jstring softwareName, jstring softwareVersion) {
  if (env == nullptr || softwareName == nullptr || softwareVersion == nullptr) {
    MKALL_THROW_EINVAL;
    return 0L;
  }
  std::string name;
  std::string version;
  if (!mkall_string_java2cxx(env, softwareName, name) ||
      !mkall_string_java2cxx(env, softwareVersion, version)) {
    return 0L;  // Exception already pending
  }
  return reinterpret_cast<jlong>(new mk::collector::Reporter{
      std::move(name), std::move(version)});
}

MKALL_SET_STRING(
    MKReporterTask_SetCABundlePath,
    [](mk::collector::Reporter *r, const char *s) noexcept {
      r->set_ca_bundle_path(s);
    },
    mk::collector::Reporter)

// mk_reporter_results contains the results of Submit
struct mk_reporter_results {
  bool good = false;
  std::vector<std::string> logs;
  std::string measurement;
  std::string report_id;
  std::string stats;
};

JNIEXPORT jlong JNICALL Java_io_ooni_mk_MKReporterTask_Submit(
    JNIEnv *env, jclass /*clazz*/, jlong handle, jstring measurement, jlong uploadTimeout) {
  if (env == nullptr || handle == 0L || measurement == nullptr) {
    MKALL_THROW_EINVAL;
    return 0L;
  }
  std::unique_ptr<mk_reporter_results> results{new mk_reporter_results{}};
  if (!mkall_string_java2cxx(env, measurement, results->measurement)) {
    return 0L;  // Exception already pending
  }
  mk::collector::Reporter::Stats stats;
  auto r = reinterpret_cast<mk::collector::Reporter *>(handle);
  results->good = r->maybe_discover_and_submit_with_stats(
      results->measurement, results->logs, uploadTimeout, stats);
  results->report_id = r->report_id();
  {
    nlohmann::json doc;
#define XX(name_) doc[#name_] = stats.name_;
    MKCOLLECTOR_REPORTER_STATS_ENUM(XX)
#undef XX
    // The following statement is unlikely to throw because the name is
    // certainly JSON serializable. The values of the stats are simple
    // int64 numbers. Therefore I don't see how it can throw.
    results->stats = doc.dump();
  }
  return reinterpret_cast<jlong>(results.release());
}

MKALL_DELETE(
    MKReporterTask_Delete,
    [](mk::collector::Reporter *r) noexcept { delete r; },
    mk::collector::Reporter);

MKALL_GET_BOOLEAN(
    MKReporterResults_Good,
    [](mk_reporter_results *r) { return r->good; },
    mk_reporter_results);

MKALL_GET_LOGS(
    MKReporterResults_Logs,
    [](mk_reporter_results *r) { return r->logs.size(); },
    [](mk_reporter_results *r, size_t idx) { return r->logs[idx].c_str(); },
    mk_reporter_results);

MKALL_GET_STRING(
    MKReporterResults_UpdatedSerializedMeasurement,
    [](mk_reporter_results *r) { return r->measurement.c_str(); },
    mk_reporter_results);

MKALL_GET_STRING(
    MKReporterResults_UpdatedReportID,
    [](mk_reporter_results *r) { return r->report_id.c_str(); },
    mk_reporter_results);

MKALL_GET_STRING(
    MKReporterResults_SerializedStats,
    [](mk_reporter_results *r) { return r->stats.c_str(); },
    mk_reporter_results);

MKALL_DELETE(
    MKReporterResults_Delete,
    [](mk_reporter_results *r) noexcept { delete r; },
    mk_reporter_results);
