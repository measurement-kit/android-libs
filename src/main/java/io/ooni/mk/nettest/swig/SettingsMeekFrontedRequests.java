/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class SettingsMeekFrontedRequests extends SettingsBaseNeedsInput {
  private transient long swigCPtr;

  protected SettingsMeekFrontedRequests(long cPtr, boolean cMemoryOwn) {
    super(ApiJNI.SettingsMeekFrontedRequests_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SettingsMeekFrontedRequests obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_SettingsMeekFrontedRequests(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public SettingsMeekFrontedRequests() {
    this(ApiJNI.new_SettingsMeekFrontedRequests(), true);
  }

  public final static String name = ApiJNI.SettingsMeekFrontedRequests_name_get();
}