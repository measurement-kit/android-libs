/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class EventStatusMeasurementDone {
  private transient long swigCPtr;
  protected transient boolean swigCMemOwn;

  protected EventStatusMeasurementDone(long cPtr, boolean cMemoryOwn) {
    swigCMemOwn = cMemoryOwn;
    swigCPtr = cPtr;
  }

  protected static long getCPtr(EventStatusMeasurementDone obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_EventStatusMeasurementDone(swigCPtr);
      }
      swigCPtr = 0;
    }
  }

  public void setIdx(long value) {
    ApiJNI.EventStatusMeasurementDone_idx_set(swigCPtr, this, value);
  }

  public long getIdx() {
    return ApiJNI.EventStatusMeasurementDone_idx_get(swigCPtr, this);
  }

  public EventStatusMeasurementDone() {
    this(ApiJNI.new_EventStatusMeasurementDone(), true);
  }

  public final static String key = ApiJNI.EventStatusMeasurementDone_key_get();
}