/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.12
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package io.ooni.mk.nettest.swig;

public class SettingsBaseNeedsInput extends SettingsBase {
  private transient long swigCPtr;

  protected SettingsBaseNeedsInput(long cPtr, boolean cMemoryOwn) {
    super(ApiJNI.SettingsBaseNeedsInput_SWIGUpcast(cPtr), cMemoryOwn);
    swigCPtr = cPtr;
  }

  protected static long getCPtr(SettingsBaseNeedsInput obj) {
    return (obj == null) ? 0 : obj.swigCPtr;
  }

  protected void finalize() {
    delete();
  }

  public synchronized void delete() {
    if (swigCPtr != 0) {
      if (swigCMemOwn) {
        swigCMemOwn = false;
        ApiJNI.delete_SettingsBaseNeedsInput(swigCPtr);
      }
      swigCPtr = 0;
    }
    super.delete();
  }

  public void addInput(String value) {
    ApiJNI.SettingsBaseNeedsInput_addInput(swigCPtr, this, value);
  }

  public void addInputFilepath(String value) {
    ApiJNI.SettingsBaseNeedsInput_addInputFilepath(swigCPtr, this, value);
  }

  public SettingsBaseNeedsInput() {
    this(ApiJNI.new_SettingsBaseNeedsInput(), true);
  }

}
