/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class SettingsTelegram extends SettingsBase {
  private transient long swigCPtr;

  protected SettingsTelegram(long cPtr, boolean cMemoryOwn) {
    super(ApiJNI.SettingsTelegram_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SettingsTelegram obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_SettingsTelegram(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public SettingsTelegram() {
    this(ApiJNI.new_SettingsTelegram(), true);
  }

  public final static String name = ApiJNI.SettingsTelegram_name_get();
}
