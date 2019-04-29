// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

/** MKOrchestraSettings contains the orchestra settings. */
public class MKOrchestraSettings {
    long handle = 0;

    final static native long New();

    final static native void SetAvailableBandwidth(long handle, String value);

    final static native void SetDeviceToken(long handle, String value);

    final static native void SetCABundlePath(long handle, String value);

    final static native void SetGeoIPCountryPath(long handle, String value);

    final static native void SetGeoIPASNPath(long handle, String value);

    final static native void SetLanguage(long handle, String value);

    final static native void SetNetworkType(long handle, String value);

    final static native void SetPlatform(long handle, String value);

    final static native void SetProbeASN(long handle, String value);

    final static native void SetProbeCC(long handle, String value);

    final static native void SetProbeFamily(long handle, String value);

    final static native void SetProbeTimezone(long handle, String value);

    final static native void SetRegistryURL(long handle, String value);

    final static native void SetSecretsFile(long handle, String value);

    final static native void SetSoftwareName(long handle, String value);

    final static native void SetSoftwareVersion(long handle, String value);

    final static native void AddSupportedTest(long handle, String value);

    final static native void SetTimeout(long handle, long value);

    final static native long Sync(long handle);

    final static native void Delete(long handle);

    /** MKOrchestraSettings creates new, default settings. */
    public MKOrchestraSettings() {
        if ((handle = New()) == 0) {
            throw new RuntimeException("MKOrchestraClient.New failed");
        }
    }

    /** setAvailableBandwidth sets the bandwidth that a probe is
     * available to commit to running measurements. */
    public void setAvailableBandwidth(String value) {
        SetAvailableBandwidth(handle, value);
    }

    /** setDeviceToken sets the token to be later used to send
     * push notifications to the device. */
    public void setDeviceToken(String value) {
        SetDeviceToken(handle, value);
    }

    /** setCABundlePath sets the path of the CA bundle to use. */
    public void setCABundlePath(String value) {
        SetCABundlePath(handle, value);
    }

    /** setGeoIPCountryPath sets the path of the MaxMind country database. */
    public void setGeoIPCountryPath(String value) {
        SetGeoIPCountryPath(handle, value);
    }

    /** setGeoIPASNPath sets the path of the MaxMind ASN database. */
    public void setGeoIPASNPath(String value) {
        SetGeoIPASNPath(handle, value);
    }

    /** setLanguage sets the device's language. */
    public void setLanguage(String value) {
        SetLanguage(handle, value);
    }

    /** setNetworkType sets the current network type. */
    public void setNetworkType(String value) {
        SetNetworkType(handle, value);
    }

    /** setPlatform sets the device's platform */
    public void setPlatform(String value) {
        SetPlatform(handle, value);
    }

    /** setProbeASN sets the ASN in which we are. */
    public void setProbeASN(String value) {
        SetProbeASN(handle, value);
    }

    /** setProbeCC sets the country code in which we are. */
    public void setProbeCC(String value) {
        SetProbeCC(handle, value);
    }

    /** setProbeFamily sets an identifier for a group of probes. */
    public void setProbeFamily(String value) {
        SetProbeFamily(handle, value);
    }

    /** setProbeTimezone sets the timezone of the probe. */
    public void setProbeTimezone(String value) {
        SetProbeTimezone(handle, value);
    }

    /** setRegistryURL sets the base URL to contact the registry. */
    public void setRegistryURL(String value) {
        SetRegistryURL(handle, value);
    }

    /** setSecretsFile sets the path of the file where to store
     * orchestra related secrets. */
    public void setSecretsFile(String value) {
        SetSecretsFile(handle, value);
    }

    /** setSoftwareName sets the name of the app. */
    public void setSoftwareName(String value) {
        SetSoftwareName(handle, value);
    }

    /** setSoftwareVersion sets the version of the app. */
    public void setSoftwareVersion(String value) {
        SetSoftwareVersion(handle, value);
    }

    /** addSupportedTest adds a test to the set of supported tests. */
    public void addSupportedTest(String value) {
        AddSupportedTest(handle, value);
    }

    /** setTimeout sets the number of seconds after which
     * outstanding requests are aborted. */
    public void setTimeout(long value) {
        SetTimeout(handle, value);
    }

    /** updateOrRegister will either update the status of the probe with
     * the registry, or register the probe with the registry, depending
     * on whether we did already register or not. */
    public MKOrchestraResults updateOrRegister() {
        long result = Sync(handle);
        if (result == 0) {
            throw new RuntimeException(
                    "MKOrchestraClient.updateOrRegister failed");
        }
        return new MKOrchestraResults(result);
    }

    @Override public synchronized void finalize() {
        Delete(handle);
    }
}
