// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.jni.sync;

/**
 * JNI API to run synchronous OONI tests.
 */
public class OoniSyncApi {

    /**
     * Runs OONI dns-injection test.
     * @param backend Address (and optionally port) of backend.
     * @param inputPath Path of input file to use.
     * @param reportPath Path of the report file.
     * @param logPath Path of log file to use.
     * @param verbose Whether to run in verbose mode.
     * @param nameServer Nameserver to use for this test.
     */
    public static native void dnsInjection(String backend,
                                           String inputPath,
                                           String reportPath,
                                           String logPath,
                                           boolean verbose,
                                           String nameServer);

    /**
     * Runs OONI http-invalid-request-line test.
     * @param backend URL of backend.
     * @param reportPath Path of the report file.
     * @param logPath Path of log file to use.
     * @param verbose Whether to run in verbose mode.
     * @param nameServer Nameserver to use for this test.
     */
    public static native void httpInvalidRequestLine(String backend,
                                                     String reportPath,
                                                     String logPath,
                                                     boolean verbose,
                                                     String nameServer);

    /**
     * Runs OONI tcp-connect test.
     * @param port Port to use.
     * @param inputPath Path of input file to use.
     * @param reportPath Path of the report file.
     * @param logPath Path of log file to use.
     * @param verbose Whether to run in verbose mode.
     * @param nameServer Nameserver to use for this test.
     */
    public static native void tcpConnect(String port,
                                         String inputPath,
                                         String reportPath,
                                         String logPath,
                                         boolean verbose,
                                         String nameServer);
}
