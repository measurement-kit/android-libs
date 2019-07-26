// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

/**
 * MKReporterResults contains the results of submitting a specific
 * OONI measurement to the OONI collector.
 */
public class MKReporterResults {
    long handle = 0;

    final static native boolean Good(long handle);

    final static native String Logs(long handle);

    final static native String UpdatedSerializedMeasurement(long handle);

    final static native String UpdatedReportID(long handle);

    final static native String SerializedStats(long handle);

    final static native void Delete(long handle);

    MKReporterResults(long n) {
        handle = n;
    }

    /** isGood returns whether we succeded. */
    public boolean isGood() {
        return Good(handle);
    }

    /** getLogs returns the logs as one-or-more newline-separated
     * lines containing only UTF-8 characters. */
    public String getLogs() {
        return Logs(handle);
    }

    /** getUpdatedSerializedMeasurement returns the serialized measurement
     * where all the fields that should have been updated, e.g., the
     * report ID, have already been updated with the new values provided
     * by the OONI collector as part of resubmitting. */
    public String getUpdatedSerializedMeasurement() {
        return UpdatedSerializedMeasurement(handle);
    }

    /** getUpdatedReportID returns the updated report ID. */
    public String getUpdatedReportID() {
        return UpdatedReportID(handle);
    }

    public String getSerializedStats() {
        return SerializedStats(handle);
    }

    @Override public synchronized void finalize() {
        Delete(handle);
    }
}
