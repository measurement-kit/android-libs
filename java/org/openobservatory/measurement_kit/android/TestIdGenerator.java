// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.android;

public class TestIdGenerator {
    private static Long next = 0L;

    public static synchronized String get_next() {
        String s = next.toString();
        next += 1;
        return s;
    }
}
