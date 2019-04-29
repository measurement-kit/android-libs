// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

/** MKGeoIPLookupSettings contains the GeoIP lookup settings. */
public class MKGeoIPLookupSettings {
    long handle = 0;

    final static native long New();

    final static native void SetTimeout(long handle, long timeout);

    final static native void SetCountryDBPath(long handle, String path);

    final static native void SetASNDBPath(long handle, String path);

    final static native void SetCABundlePath(long handle, String path);

    final static native long Perform(long handle);

    final static native void Delete(long handle);

    /** MKGeoIPLookupSettings constructs new, default settings. */
    public MKGeoIPLookupSettings() {
        handle = New();
        if (handle == 0) {
            throw new RuntimeException("MKGeoIPLookupSettings.New failed");
        }
    }

    /** setTimeout sets the number of seconds after which pending
     * requests are aborted by Measurement Kit. */
    public void setTimeout(long timeout) {
        SetTimeout(handle, timeout);
    }

    /** setCABundlePath sets the path of the CA bundle to use. */
    public void setCABundlePath(String path) {
        SetCABundlePath(handle, path);
    }

    /** setCountryDBPath sets the path of the MaxMind country
     * database to use. */
    public void setCountryDBPath(String path) {
        SetCountryDBPath(handle, path);
    }

    /** setASNDBPath sets the path of the MaxMind ASN
     * database to use. */
    public void setASNDBPath(String path) {
        SetASNDBPath(handle, path);
    }

    @Override public synchronized void finalize() {
        Delete(handle);
    }

    /** perform performs a GeoIP lookup with current settings. */
    public MKGeoIPLookupResults perform() {
        long results = Perform(handle);
        if (results == 0) {
            throw new RuntimeException("MKGeoIPLookupSettings.Perform failed");
        }
        return new MKGeoIPLookupResults(results);
    }
}
