// Part of Measurement Kit <https://measurement-kit.github.io/>.
// Measurement Kit is free software under the BSD license. See AUTHORS
// and LICENSE for more information on the copying conditions.
package io.ooni.mk;

/** MKCollectorResubmitSettings contains the settings configuring
 * the resubmission of a report with the OONI collector. */
public class MKCollectorResubmitSettings {
    long handle = 0;

    final static native long New();

    final static native void SetTimeout(long handle, long timeout);

    final static native void SetCABundlePath(long handle, String path);

    final static native void SetContent(long handle, String content);

    final static native long Perform(long handle);

    final static native void Delete(long handle);

    /** MKCollectorResubmitSettings constructs new, default settings. */
    public MKCollectorResubmitSettings() {
        handle = New();
        if (handle == 0) {
            throw new RuntimeException(
                    "MKCollectorResubmitSettings.New failed");
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

    /** setSerializedMeasurement sets the serialized measurement that
     * you want to resubmit to the OONI collector. */
    public void setSerializedMeasurement(String measurement) {
        SetContent(handle, measurement);
    }

    @Override public synchronized void finalize() {
        Delete(handle);
    }

    /** perform resubmits the configured serialized measurements with current
     * settings to the OONI collector and returns the results. */
    public MKCollectorResubmitResults perform() {
        long results = Perform(handle);
        if (results == 0) {
            throw new RuntimeException(
                    "MKCollectorResubmitSettings.Perform failed");
        }
        return new MKCollectorResubmitResults(results);
    }
}
