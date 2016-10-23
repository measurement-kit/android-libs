// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.android;

public class LoadLibraryUtils {

    // You can call this function more than once without harm. For more
    // on this topic, see <http://stackoverflow.com/a/21879539>.
    public static void load_measurement_kit() {
        System.loadLibrary("measurement_kit");
    }
}
