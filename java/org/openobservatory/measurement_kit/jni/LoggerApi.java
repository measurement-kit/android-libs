// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.jni;

/**
 * Access to MeasurementKit Logger functionality
 */
public class LoggerApi {

    /**
     * Control verbosity of the default logger.
     * @param verbose Make verbose if true, quiet if false.
     */
    public static native void setVerbose(int verbose);

    /**
     * Redirect default logger's output to Android logcat.
     */
    public static native void useAndroidLogger();
}
