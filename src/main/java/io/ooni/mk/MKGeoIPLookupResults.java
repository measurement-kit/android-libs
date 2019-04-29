// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

/** MKGeoIPLookupResults contains the results of a GeoIP lookup. */
public class MKGeoIPLookupResults {
    long handle = 0;

    final static native boolean Good(long handle);

    final static native String GetProbeIP(long handle);

    final static native String GetProbeASN(long handle);

    final static native String GetProbeCC(long handle);

    final static native String GetProbeOrg(long handle);

    final static native String GetLogs(long handle);

    final static native void Delete(long handle);

    MKGeoIPLookupResults(long n) {
        handle = n;
    }

    /** isGood returns whether we succeded. */
    public boolean isGood() {
        return Good(handle);
    }

    /** getProbeIP returns the probe IP. */
    public String getProbeIP() {
        return GetProbeIP(handle);
    }

    /** getProbeASN returns the probe ASN. */
    public String getProbeASN() {
        return GetProbeASN(handle);
    }

    /** getProbeCC returns the probe CC. */
    public String getProbeCC() {
        return GetProbeCC(handle);
    }

    /** getProbeOrg returns the probe ASN organization. */
    public String getProbeOrg() {
        return GetProbeOrg(handle);
    }

    /** getLogs returns the logs as one or more newline separated
     * lines containing only UTF-8 characters. */
    public String getLogs() {
        return GetLogs(handle);
    }

    @Override public synchronized void finalize() {
        Delete(handle);
    }
}
