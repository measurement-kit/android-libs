/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class EventStatusReportCreate {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected EventStatusReportCreate(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(EventStatusReportCreate obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_EventStatusReportCreate(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setReportId(String value) {
    ApiJNI.EventStatusReportCreate_reportId_set(swigCPtr, this, value);
  }

  public String getReportId() {
    return ApiJNI.EventStatusReportCreate_reportId_get(swigCPtr, this);
  }

  public EventStatusReportCreate() {
    this(ApiJNI.new_EventStatusReportCreate(), true);
  }

  public final static String key = ApiJNI.EventStatusReportCreate_key_get();
}