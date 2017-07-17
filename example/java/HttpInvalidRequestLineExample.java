// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

import org.openobservatory.measurement_kit.swig.HttpInvalidRequestLineTest;
import org.openobservatory.measurement_kit.swig.LogSeverity;

public class HttpInvalidRequestLineExample {
    public static void main(String[] args) {
        System.loadLibrary("measurement_kit_java");
        HttpInvalidRequestLineTest t = new HttpInvalidRequestLineTest();
        t.set_verbosity(LogSeverity.LOG_DEBUG);
        t.run();
    }
}
