/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class NettestTelegram extends NettestBase {
  private transient long swigCPtr;

  protected NettestTelegram(long cPtr, boolean cMemoryOwn) {
    super(ApiJNI.NettestTelegram_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(NettestTelegram obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_NettestTelegram(swigCPtr);
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
    ApiJNI.NettestTelegram_change_ownership(this, swigCPtr, false);
  }

  public void swigTakeOwnership() {
    swigCMemOwn = true;
    ApiJNI.NettestTelegram_change_ownership(this, swigCPtr, true);
  }

  public NettestTelegram(SettingsTelegram arg0) {
    this(ApiJNI.new_NettestTelegram(SettingsTelegram.getCPtr(arg0), arg0), true);
    ApiJNI.NettestTelegram_director_connect(this, swigCPtr, swigCMemOwn, true);
  }

  public boolean run() {
    return (getClass() == NettestTelegram.class) ? ApiJNI.NettestTelegram_run(swigCPtr, this) : ApiJNI.NettestTelegram_runSwigExplicitNettestTelegram(swigCPtr, this);
  }

}