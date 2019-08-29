// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

#include "io_ooni_mk_MKGeoIPLookupResults.h"
#include "io_ooni_mk_MKGeoIPLookupTask.h"

#include <limits.h>

#include <measurement_kit/internal/vendor/mkgeoip.hpp>

#include "mkall_util.h"

MKALL_GET_BOOLEAN(MKGeoIPLookupResults_Good,
                  [](auto r) noexcept { return r->good; },
                  mk::geoip::LookupResults)

MKALL_GET_STRING(MKGeoIPLookupResults_GetProbeIP,
                 [](auto r) noexcept { return r->probe_ip.c_str(); },
                 mk::geoip::LookupResults)

MKALL_GET_STRING(MKGeoIPLookupResults_GetProbeASN,
                 [](auto r) noexcept { return r->probe_asn_string.c_str(); },
                 mk::geoip::LookupResults)

MKALL_GET_STRING(MKGeoIPLookupResults_GetProbeCC,
                 [](auto r) noexcept { return r->probe_cc.c_str(); },
                 mk::geoip::LookupResults)

MKALL_GET_STRING(MKGeoIPLookupResults_GetProbeOrg,
                 [](auto r) noexcept { return r->probe_org.c_str(); },
                 mk::geoip::LookupResults)

MKALL_GET_LOGS(MKGeoIPLookupResults_GetLogs,
               [](auto r) noexcept { return r->logs.size(); },
               [](auto r, size_t i) noexcept { return r->logs[i].c_str(); },
               mk::geoip::LookupResults)

MKALL_DELETE(MKGeoIPLookupResults_Delete,
             [](auto r) noexcept { delete r; },
             mk::geoip::LookupResults)

MKALL_NEW(MKGeoIPLookupTask_New, []() noexcept {
    return new mk::geoip::LookupSettings{};
})

MKALL_SET_LONG(MKGeoIPLookupTask_SetTimeout,
               [](auto s, auto v) noexcept { s->timeout = v; },
               mk::geoip::LookupSettings)

MKALL_SET_STRING(MKGeoIPLookupTask_SetCountryDBPath,
                 [](auto s, auto v) noexcept { s->country_db_path = v; },
                 mk::geoip::LookupSettings)

MKALL_SET_STRING(MKGeoIPLookupTask_SetASNDBPath,
                 [](auto s, auto v) noexcept { s->asn_db_path = v; },
                 mk::geoip::LookupSettings)

MKALL_SET_STRING(MKGeoIPLookupTask_SetCABundlePath,
                 [](auto s, auto v) noexcept { s->ca_bundle_path = v; },
                 mk::geoip::LookupSettings)

MKALL_GET_POINTER(MKGeoIPLookupTask_Perform, [](auto s) noexcept {
  auto r = new mk::geoip::LookupResults{mk::geoip::lookup(*s)};
  if (!r->good) {
    // Implementation note: the logic used by mk::geoip::lookup to determine
    // success checks the values of the probe_ip, probe_asn, etc fields. This
    // means we cannot initialize the results to the values we want to have
    // back on failure ("ZZ", "AS0", etc). We should instead check whether we
    // are good and set them afterwards. This glue code can probably go, if
    // we include this logic into the next release of mkgeoip.
    //
    // This code has been adapted from MK v0.10.4's geoiplookup.c. We should
    // actually fix geoiplookup to avoid duplicating code.
    //
    // Issue filed as <https://github.com/measurement-kit/mkgeoip/issues/18>.
    if (r->probe_asn_string.empty()) {
      r->probe_asn_string = "AS0";
    }
    if (r->probe_cc.empty()) {
      r->probe_cc = "ZZ";
    }
  }
  return r;
}, mk::geoip::LookupSettings)

MKALL_DELETE(MKGeoIPLookupTask_Delete,
             [](auto s) noexcept { delete s; },
             mk::geoip::LookupSettings)
