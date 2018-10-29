package io.ooni.mk;

public class MKGeoIPLookupSettings {
  long handle = 0;

  final static native long New();

  final static native void SetTimeout(long handle, long timeout);

  final static native void SetCountryDBPath(long handle, String path);

  final static native void SetASNDBPath(long handle, String path);

  final static native void SetCABundlePath(long handle, String path);

  final static native long Perform(long handle);

  final static native void Delete(long handle);

  public MKGeoIPLookupSettings() {
    if ((handle = New()) == 0) {
      throw new RuntimeException("MKGeoIPLookupSettings.New failed");
    }
  }

  public void setTimeout(long timeout) {
    SetTimeout(handle, timeout);
  }

  public void setCABundlePath(String path) {
    SetCABundlePath(handle, path);
  }

  public void setCountryDBPath(String path) {
    SetCountryDBPath(handle, path);
  }

  public void setASNDBPath(String path) {
    SetASNDBPath(handle, path);
  }

  @Override public synchronized void finalize() {
    Delete(handle);
  }

  MKGeoIPLookupResults perform() {
    long results = Perform(handle);
    if (results == 0) {
      throw new RuntimeException("MKGeoIPLookupSettings.Perform failed");
    }
    return new MKGeoIPLookupResults(results);
  }
}
