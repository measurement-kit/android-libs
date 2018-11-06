// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

public class MKOrchestraResult {
  long handle = 0;

  final static native boolean Good(long handle);

  final static native byte[] GetBinaryLogs(long handle);

  final static native void Delete(long handle);

  protected MKOrchestraResult(long n) {
    handle = n;
  }

  public boolean good() {
    return Good(handle);
  }

  public byte[] getBinaryLogs() {
    return GetBinaryLogs(handle);
  }

  @Override public synchronized void finalize() {
    Delete(handle);
  }
}
