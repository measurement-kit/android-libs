/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class NettestCaptivePortal extends NettestBase {
  private transient long swigCPtr;

  protected NettestCaptivePortal(long cPtr, boolean cMemoryOwn) {
    super(ApiJNI.NettestCaptivePortal_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(NettestCaptivePortal obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_NettestCaptivePortal(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  protected void swigDirectorDisconnect() {
    swigCMemOwn = false;
    delete();
  }

  public void swigReleaseOwnership() {
    swigCMemOwn = false;
    ApiJNI.NettestCaptivePortal_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    ApiJNI.NettestCaptivePortal_change_ownership(this, swigCPtr, true);
  }

  public NettestCaptivePortal(SettingsCaptivePortal arg0) {
    this(ApiJNI.new_NettestCaptivePortal(SettingsCaptivePortal.getCPtr(arg0), arg0), true);
    ApiJNI.NettestCaptivePortal_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

  public boolean run() {
    return (getClass() == NettestCaptivePortal.class) ? ApiJNI.NettestCaptivePortal_run(swigCPtr, this) : ApiJNI.NettestCaptivePortal_runSwigExplicitNettestCaptivePortal(swigCPtr, this);
  }

}