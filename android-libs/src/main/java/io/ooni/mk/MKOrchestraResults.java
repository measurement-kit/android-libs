// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

/** MKOrchestraResults contains the results of an orchestra operation. */
public class MKOrchestraResults {
    long handle = 0;

    final static native boolean Good(long handle);

    final static native String GetLogs(long handle);

    final static native void Delete(long handle);

    MKOrchestraResults(long n) {
        handle = n;
    }

    /** isGood indicates whether we succeeded. */
    public boolean isGood() {
        return Good(handle);
    }

    /** getLogs returns the logs as one or more UTF-8 lines of text. */
    public String getLogs() {
        return GetLogs(handle);
    }

    @Override public synchronized void finalize() {
        Delete(handle);
    }
}
