// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.sync;

/**
 * JNI API to run synchronous Portolan tests.
 * @deprecated This is an old API that will be removed in v0.4
 */
public class PortolanSyncApi {

    /**
     * Open traceroute prober (i.e. opaque object to send probes).
     * @param useIpv4 whether to use IPv4.
     * @param sourcePort Specify source port.
     * @return opaque prober on success, zero on failure.
     */
    public static native long openProber(boolean useIpv4, int sourcePort);

    /**
     * Send single traceroute probe.
     *
     * @param prober Opaque traceroute prober instance.
     * @param destIp Destination IP address.
     * @param ttl Time to live.
     * @param timeout Time to wait for response.
     *
     * @param outStrings Vector of strings filled by this function that will
     *        contain the statusCode as first element and the interfaceIp
     *        as second element. As of MeasurementKit 0.1.0, the status code
     *        should be one of the following:
     *
     *            - OTHER
     *            - NO_ROUTE_TO_HOST
     *            - ADDRESS_UNREACH
     *            - PROTO_NOT_IMPL
     *            - PORT_IS_CLOSED
     *            - TTL_EXCEEDED
     *            - ADMIN_FILTER
     *            - GOT_REPLY_PACKET
     *
     * @param outInts Vector of integers filled by this function that will
     *        contain the ttl, the number of received bytes, and whether
     *        the response is IPv4 (the latter with the semantic that zero
     *        is false and nonzero is true).
     *
     * @param outDouble On success this vector will be filled with the RTT
     *        of the response in position 0.
     */
    public static native void sendProbe(long prober,
                                        String destIp,
                                        int destPort,
                                        int ttl,
                                        double timeout,
                                        String[
                                            // statusCode
                                            // interfaceIp
                                        ] outStrings,
                                        int[
                                            // ttl
                                            // recvBytes
                                            // isIpv4
                                        ] outInts,
                                        double[
                                            // rtt
                                        ] outDoubles);

    /**
     * Close opaque traceroute prober instance.
     * @param prober Opaque traceroute prober instance.
     */
    public static native void closeProber(long prober);

    /**
     * Checks whether a TCP port is open.
     * @param useIpv4 Whether to use IPv4 or not.
     * @param addr Address to use.
     * @param port Port to use.
     * @param timeo Connect() timeout.
     * @param verbose Whether to run test in verbose mode.
     * @return True if port is open, false otherwise.
     */
    public static native boolean checkPort(boolean useIpv4, String addr,
                                           String port, double timeo,
                                           boolean verbose);
}
