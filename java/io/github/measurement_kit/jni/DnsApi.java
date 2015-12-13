// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package io.github.measurement_kit.jni;

/**
 * Access to MeasurementKit DNS functionality
 */
public class DnsApi {

    /**
     * Clear all configured nameservers.
     */
    public static native void clearNameServers();

    /**
     * Count number of registered nameservers
     * @return Number of registered nameservers
     */
    public static native int countNameServers();

    /**
     * Add name-server to the set of registered name-servers.
     * @param nameServer Address with optional port (e.g. "8.8.8.8:53").
     */
    public static native void addNameServer(String nameServer);
}
