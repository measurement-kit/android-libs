/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class EventStatusMeasurementSubmission {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected EventStatusMeasurementSubmission(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(EventStatusMeasurementSubmission obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_EventStatusMeasurementSubmission(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setIdx(long value) {
    ApiJNI.EventStatusMeasurementSubmission_idx_set(swigCPtr, this, value);
  }

  public long getIdx() {
    return ApiJNI.EventStatusMeasurementSubmission_idx_get(swigCPtr, this);
  }

  public EventStatusMeasurementSubmission() {
    this(ApiJNI.new_EventStatusMeasurementSubmission(), true);
  }

  public final static String key = ApiJNI.EventStatusMeasurementSubmission_key_get();
}
