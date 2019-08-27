// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

/**
 * MKReporterTask is a sync task for submitting OONI measurements
 * to the OONI collector.
 */
public class MKReporterTask {
    long handle = 0;

    final static native long New(String softwareName, String softwareVersion);

    final static native void SetCABundlePath(long handle, String path);

    final static native long Submit(long handle, String measurement, long timeout);

    final static native void Delete(long handle);

    /** MKReporterTask creates a new reporter task. */
    public MKReporterTask(String softwareName, String softwareVersion,
                          String caBundlePath) {
        handle = New(softwareName, softwareVersion);
        if (handle == 0) {
            throw new RuntimeException(
                    "MKReporterTask.New failed");
        }
        SetCABundlePath(handle, caBundlePath);
    }

    /** submit submites a measurement and returns the results. This method will
     * automatically discover a collector, if none is specified. */
    public MKReporterResults submit(String measurement, long timeout) {
        long results = Submit(handle, measurement, timeout);
        if (results == 0) {
            throw new RuntimeException(
                    "MKReporterTask.Submit failed");
        }
        return new MKReporterResults(results);
    }

    @SuppressWarnings("deprecation")
    @Override public synchronized void finalize() {
        Delete(handle);
    }
}
