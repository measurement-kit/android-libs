// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package io.ooni.mk.nettest.common;

import java.util.Map;

public class Settings {
  public Map<String, String> annotations;
  public String logFilepath;
  public String logLevel;
  public String outputFilepath;
  public String bouncerBaseUrl;
  public String collectorBaseUrl;
  public String dnsNameserver;
  public String dnsEngine;
  public String geoipAnsPath;
  public String geoipCountryPath;
  public boolean ignoreBouncerError;
  public boolean ignoreOpenReportError;
  public double maxRuntime;
  public String caBundlePath;
  public double netTimeout;
  public boolean noBouncer;
  public boolean noCollector;
  public boolean noAsnLookup;
  public boolean noCcLookup;

  public void setNo_cc_lookup(boolean value) {
    mk_swig_nettestJNI.Settings_no_cc_lookup_set(swigCPtr, this, value);
  }

  public boolean getNo_cc_lookup() {
    return mk_swig_nettestJNI.Settings_no_cc_lookup_get(swigCPtr, this);
  }

  public void setNo_ip_lookup(boolean value) {
    mk_swig_nettestJNI.Settings_no_ip_lookup_set(swigCPtr, this, value);
  }

  public boolean getNo_ip_lookup() {
    return mk_swig_nettestJNI.Settings_no_ip_lookup_get(swigCPtr, this);
  }

  public void setNo_file_report(boolean value) {
    mk_swig_nettestJNI.Settings_no_file_report_set(swigCPtr, this, value);
  }

  public boolean getNo_file_report() {
    return mk_swig_nettestJNI.Settings_no_file_report_get(swigCPtr, this);
  }

  public void setNo_resolver_lookup(boolean value) {
    mk_swig_nettestJNI.Settings_no_resolver_lookup_set(swigCPtr, this, value);
  }

  public boolean getNo_resolver_lookup() {
    return mk_swig_nettestJNI.Settings_no_resolver_lookup_get(swigCPtr, this);
  }

  public void setProbe_asn(String value) {
    mk_swig_nettestJNI.Settings_probe_asn_set(swigCPtr, this, value);
  }

  public String getProbe_asn() {
    return mk_swig_nettestJNI.Settings_probe_asn_get(swigCPtr, this);
  }

  public void setProbe_cc(String value) {
    mk_swig_nettestJNI.Settings_probe_cc_set(swigCPtr, this, value);
  }

  public String getProbe_cc() {
    return mk_swig_nettestJNI.Settings_probe_cc_get(swigCPtr, this);
  }

  public void setProbe_ip(String value) {
    mk_swig_nettestJNI.Settings_probe_ip_set(swigCPtr, this, value);
  }

  public String getProbe_ip() {
    return mk_swig_nettestJNI.Settings_probe_ip_get(swigCPtr, this);
  }

  public void setRandomize_input(boolean value) {
    mk_swig_nettestJNI.Settings_randomize_input_set(swigCPtr, this, value);
  }

  public boolean getRandomize_input() {
    return mk_swig_nettestJNI.Settings_randomize_input_get(swigCPtr, this);
  }

  public void setSave_real_probe_asn(boolean value) {
    mk_swig_nettestJNI.Settings_save_real_probe_asn_set(swigCPtr, this, value);
  }

  public boolean getSave_real_probe_asn() {
    return mk_swig_nettestJNI.Settings_save_real_probe_asn_get(swigCPtr, this);
  }

  public void setSave_real_probe_cc(boolean value) {
    mk_swig_nettestJNI.Settings_save_real_probe_cc_set(swigCPtr, this, value);
  }

  public boolean getSave_real_probe_cc() {
    return mk_swig_nettestJNI.Settings_save_real_probe_cc_get(swigCPtr, this);
  }

  public void setSave_real_probe_ip(boolean value) {
    mk_swig_nettestJNI.Settings_save_real_probe_ip_set(swigCPtr, this, value);
  }

  public boolean getSave_real_probe_ip() {
    return mk_swig_nettestJNI.Settings_save_real_probe_ip_get(swigCPtr, this);
  }

  public void setSave_real_resolver_ip(boolean value) {
    mk_swig_nettestJNI.Settings_save_real_resolver_ip_set(swigCPtr, this, value);
  }

  public boolean getSave_real_resolver_ip() {
    return mk_swig_nettestJNI.Settings_save_real_resolver_ip_get(swigCPtr, this);
  }

  public void setSoftware_name(String value) {
    mk_swig_nettestJNI.Settings_software_name_set(swigCPtr, this, value);
  }

  public String getSoftware_name() {
    return mk_swig_nettestJNI.Settings_software_name_get(swigCPtr, this);
  }

  public void setSoftware_version(String value) {
    mk_swig_nettestJNI.Settings_software_version_set(swigCPtr, this, value);
  }

  public String getSoftware_version() {
    return mk_swig_nettestJNI.Settings_software_version_get(swigCPtr, this);
  }

  public Settings() {
    this(mk_swig_nettestJNI.new_Settings(), true);
  }

}
