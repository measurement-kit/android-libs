// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

public class MKGeoIPLookupResults {
  long handle = 0;

  final static native boolean Good(long handle);

  final static native double GetBytesSent(long handle);

  final static native double GetBytesRecv(long handle);

  final static native String GetProbeIP(long handle);

  final static native long GetProbeASN(long handle);

  final static native String GetProbeCC(long handle);

  final static native String GetProbeOrg(long handle);

  final static native byte[] GetLogs(long handle);

  final static native void Delete(long handle);

  protected MKGeoIPLookupResults(long n) {
    handle = n;
  }

  public boolean good() {
    return Good(handle);
  }

  public double getBytesSent() {
    return GetBytesSent(handle);
  }

  public double getBytesRecv() {
    return GetBytesRecv(handle);
  }

  public String getProbeIP() {
    return GetProbeIP(handle);
  }

  public long getProbeASN() {
    return GetProbeASN(handle);
  }

  public String getProbeCC() {
    return GetProbeCC(handle);
  }

  public String getProbeOrg() {
    return GetProbeOrg(handle);
  }

  public byte[] getLogs() {
    return GetLogs(handle);
  }

  @Override public synchronized void finalize() {
    Delete(handle);
  }
}
