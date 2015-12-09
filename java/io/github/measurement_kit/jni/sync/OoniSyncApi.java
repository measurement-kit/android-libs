// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package io.github.measurement_kit.jni.sync;

/**
 * JNI API to run synchronous OONI tests.
 */
public class OoniSyncApi {

    /**
     * Runs OONI dns-injection test.
     * @param backend Address (and optionally port) of backend.
     * @param inputPath Path of input file to use.
     * @param verbose Whether to run in verbose mode.
     * @param logPath Path of log file to use.
     * @return Path of test report file.
     */
    public static native String dnsInjection(String backend,
                                             String inputPath,
                                             boolean verbose,
                                             String logPath);

    /**
     * Runs OONI http-invalid-request-line test.
     * @param backend URL of backend.
     * @param verbose Whether to run in verbose mode.
     * @param logPath Path of log file to use.
     * @return Path of test report file.
     */
    public static native String httpInvalidRequestLine(String backend,
                                                       boolean verbose,
                                                       String logPath);

    /**
     * Runs OONI tcp-connect test.
     * @param port Port to use.
     * @param inputPath Path of input file to use.
     * @param verbose Whether to run in verbose mode.
     * @param logPath Path of log file to use.
     * @return Path of test report file.
     */
    public static native String tcpConnect(String port,
                                           String inputPath,
                                           boolean verbose,
                                           String logPath);
}
