/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class NettestNdt extends NettestBasePerformance {
  private transient long swigCPtr;

  protected NettestNdt(long cPtr, boolean cMemoryOwn) {
    super(ApiJNI.NettestNdt_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(NettestNdt obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_NettestNdt(swigCPtr);
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
    ApiJNI.NettestNdt_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    ApiJNI.NettestNdt_change_ownership(this, swigCPtr, true);
  }

  public NettestNdt(SettingsNdt arg0) {
    this(ApiJNI.new_NettestNdt(SettingsNdt.getCPtr(arg0), arg0), true);
    ApiJNI.NettestNdt_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

  public boolean run() {
    return (getClass() == NettestNdt.class) ? ApiJNI.NettestNdt_run(swigCPtr, this) : ApiJNI.NettestNdt_runSwigExplicitNettestNdt(swigCPtr, this);
  }

}