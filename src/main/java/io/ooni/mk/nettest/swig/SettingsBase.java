/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class SettingsBase {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected SettingsBase(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SettingsBase obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_SettingsBase(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setLogFilepath(String value) {
    ApiJNI.SettingsBase_logFilepath_set(swigCPtr, this, value);
  }

  public String getLogFilepath() {
    return ApiJNI.SettingsBase_logFilepath_get(swigCPtr, this);
  }

  public void setLogLevel(String value) {
    ApiJNI.SettingsBase_logLevel_set(swigCPtr, this, value);
  }

  public String getLogLevel() {
    return ApiJNI.SettingsBase_logLevel_get(swigCPtr, this);
  }

  public void setOutputFilepath(String value) {
    ApiJNI.SettingsBase_outputFilepath_set(swigCPtr, this, value);
  }

  public String getOutputFilepath() {
    return ApiJNI.SettingsBase_outputFilepath_get(swigCPtr, this);
  }

  public void setBouncerBaseUrl(String value) {
    ApiJNI.SettingsBase_bouncerBaseUrl_set(swigCPtr, this, value);
  }

  public String getBouncerBaseUrl() {
    return ApiJNI.SettingsBase_bouncerBaseUrl_get(swigCPtr, this);
  }

  public void setCollectorBaseUrl(String value) {
    ApiJNI.SettingsBase_collectorBaseUrl_set(swigCPtr, this, value);
  }

  public String getCollectorBaseUrl() {
    return ApiJNI.SettingsBase_collectorBaseUrl_get(swigCPtr, this);
  }

  public void setDnsNameserver(String value) {
    ApiJNI.SettingsBase_dnsNameserver_set(swigCPtr, this, value);
  }

  public String getDnsNameserver() {
    return ApiJNI.SettingsBase_dnsNameserver_get(swigCPtr, this);
  }

  public void setDnsEngine(String value) {
    ApiJNI.SettingsBase_dnsEngine_set(swigCPtr, this, value);
  }

  public String getDnsEngine() {
    return ApiJNI.SettingsBase_dnsEngine_get(swigCPtr, this);
  }

  public void setGeoipAsnPath(String value) {
    ApiJNI.SettingsBase_geoipAsnPath_set(swigCPtr, this, value);
  }

  public String getGeoipAsnPath() {
    return ApiJNI.SettingsBase_geoipAsnPath_get(swigCPtr, this);
  }

  public void setGeoipCountryPath(String value) {
    ApiJNI.SettingsBase_geoipCountryPath_set(swigCPtr, this, value);
  }

  public String getGeoipCountryPath() {
    return ApiJNI.SettingsBase_geoipCountryPath_get(swigCPtr, this);
  }

  public void setIgnoreBouncerError(boolean value) {
    ApiJNI.SettingsBase_ignoreBouncerError_set(swigCPtr, this, value);
  }

  public boolean getIgnoreBouncerError() {
    return ApiJNI.SettingsBase_ignoreBouncerError_get(swigCPtr, this);
  }

  public void setIgnoreOpenReportError(boolean value) {
    ApiJNI.SettingsBase_ignoreOpenReportError_set(swigCPtr, this, value);
  }

  public boolean getIgnoreOpenReportError() {
    return ApiJNI.SettingsBase_ignoreOpenReportError_get(swigCPtr, this);
  }

  public void setMaxRuntime(double value) {
    ApiJNI.SettingsBase_maxRuntime_set(swigCPtr, this, value);
  }

  public double getMaxRuntime() {
    return ApiJNI.SettingsBase_maxRuntime_get(swigCPtr, this);
  }

  public void setNetCaBundlePath(String value) {
    ApiJNI.SettingsBase_netCaBundlePath_set(swigCPtr, this, value);
  }

  public String getNetCaBundlePath() {
    return ApiJNI.SettingsBase_netCaBundlePath_get(swigCPtr, this);
  }

  public void setNetTimeout(double value) {
    ApiJNI.SettingsBase_netTimeout_set(swigCPtr, this, value);
  }

  public double getNetTimeout() {
    return ApiJNI.SettingsBase_netTimeout_get(swigCPtr, this);
  }

  public void setNoBouncer(boolean value) {
    ApiJNI.SettingsBase_noBouncer_set(swigCPtr, this, value);
  }

  public boolean getNoBouncer() {
    return ApiJNI.SettingsBase_noBouncer_get(swigCPtr, this);
  }

  public void setNoCollector(boolean value) {
    ApiJNI.SettingsBase_noCollector_set(swigCPtr, this, value);
  }

  public boolean getNoCollector() {
    return ApiJNI.SettingsBase_noCollector_get(swigCPtr, this);
  }

  public void setNoAsnLookup(boolean value) {
    ApiJNI.SettingsBase_noAsnLookup_set(swigCPtr, this, value);
  }

  public boolean getNoAsnLookup() {
    return ApiJNI.SettingsBase_noAsnLookup_get(swigCPtr, this);
  }

  public void setNoCcLookup(boolean value) {
    ApiJNI.SettingsBase_noCcLookup_set(swigCPtr, this, value);
  }

  public boolean getNoCcLookup() {
    return ApiJNI.SettingsBase_noCcLookup_get(swigCPtr, this);
  }

  public void setNoIpLookup(boolean value) {
    ApiJNI.SettingsBase_noIpLookup_set(swigCPtr, this, value);
  }

  public boolean getNoIpLookup() {
    return ApiJNI.SettingsBase_noIpLookup_get(swigCPtr, this);
  }

  public void setNoFileReport(boolean value) {
    ApiJNI.SettingsBase_noFileReport_set(swigCPtr, this, value);
  }

  public boolean getNoFileReport() {
    return ApiJNI.SettingsBase_noFileReport_get(swigCPtr, this);
  }

  public void setNoResolverLookup(boolean value) {
    ApiJNI.SettingsBase_noResolverLookup_set(swigCPtr, this, value);
  }

  public boolean getNoResolverLookup() {
    return ApiJNI.SettingsBase_noResolverLookup_get(swigCPtr, this);
  }

  public void setProbeAsn(String value) {
    ApiJNI.SettingsBase_probeAsn_set(swigCPtr, this, value);
  }

  public String getProbeAsn() {
    return ApiJNI.SettingsBase_probeAsn_get(swigCPtr, this);
  }

  public void setProbeCc(String value) {
    ApiJNI.SettingsBase_probeCc_set(swigCPtr, this, value);
  }

  public String getProbeCc() {
    return ApiJNI.SettingsBase_probeCc_get(swigCPtr, this);
  }

  public void setProbeIp(String value) {
    ApiJNI.SettingsBase_probeIp_set(swigCPtr, this, value);
  }

  public String getProbeIp() {
    return ApiJNI.SettingsBase_probeIp_get(swigCPtr, this);
  }

  public void setRandomizeInput(boolean value) {
    ApiJNI.SettingsBase_randomizeInput_set(swigCPtr, this, value);
  }

  public boolean getRandomizeInput() {
    return ApiJNI.SettingsBase_randomizeInput_get(swigCPtr, this);
  }

  public void setSaveRealProbeAsn(boolean value) {
    ApiJNI.SettingsBase_saveRealProbeAsn_set(swigCPtr, this, value);
  }

  public boolean getSaveRealProbeAsn() {
    return ApiJNI.SettingsBase_saveRealProbeAsn_get(swigCPtr, this);
  }

  public void setSaveRealProbeCc(boolean value) {
    ApiJNI.SettingsBase_saveRealProbeCc_set(swigCPtr, this, value);
  }

  public boolean getSaveRealProbeCc() {
    return ApiJNI.SettingsBase_saveRealProbeCc_get(swigCPtr, this);
  }

  public void setSaveRealProbeIp(boolean value) {
    ApiJNI.SettingsBase_saveRealProbeIp_set(swigCPtr, this, value);
  }

  public boolean getSaveRealProbeIp() {
    return ApiJNI.SettingsBase_saveRealProbeIp_get(swigCPtr, this);
  }

  public void setSaveRealResolverIp(boolean value) {
    ApiJNI.SettingsBase_saveRealResolverIp_set(swigCPtr, this, value);
  }

  public boolean getSaveRealResolverIp() {
    return ApiJNI.SettingsBase_saveRealResolverIp_get(swigCPtr, this);
  }

  public void setSoftwareName(String value) {
    ApiJNI.SettingsBase_softwareName_set(swigCPtr, this, value);
  }

  public String getSoftwareName() {
    return ApiJNI.SettingsBase_softwareName_get(swigCPtr, this);
  }

  public void setSoftwareVersion(String value) {
    ApiJNI.SettingsBase_softwareVersion_set(swigCPtr, this, value);
  }

  public String getSoftwareVersion() {
    return ApiJNI.SettingsBase_softwareVersion_get(swigCPtr, this);
  }

  public void addAnnotation(String key, String value) {
    ApiJNI.SettingsBase_addAnnotation(swigCPtr, this, key, value);
  }

  public SettingsBase() {
    this(ApiJNI.new_SettingsBase(), true);
  }

}
