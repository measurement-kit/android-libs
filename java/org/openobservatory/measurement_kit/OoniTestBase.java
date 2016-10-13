// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit;

public class OoniTestBase {
    private OoniTestWrapper wrapper = null;

    public OoniTestBase(String test_name) {
        wrapper = new OoniTestWrapper(test_name);
    }

    public OoniTestBase set_verbosity(long verbosity) {
        wrapper.set_verbosity(verbosity);
        return this;
    }

    public OoniTestBase increase_verbosity() {
        wrapper.increase_verbosity();
        return this;
    }

    public OoniTestBase set_input_filepath(String fpath) {
        wrapper.set_input_filepath(fpath);
        return this;
    }

    public OoniTestBase set_output_filepath(String fpath) {
        wrapper.set_output_filepath(fpath);
        return this;
    }

    public OoniTestBase set_error_filepath(String fpath) {
        wrapper.set_error_filepath(fpath);
        return this;
    }

    public OoniTestBase use_logcat() {
        wrapper.use_logcat();
        return this;
    }

    public OoniTestBase on_log(LogCallback delegate) {
        wrapper.on_log(delegate);
        return this;
    }

    public void run(TestCompleteCallback callback) {
        wrapper.run(callback);
    }

    public OoniTestBase set_options(String key, String value) {
        wrapper.set_options(key, value);
        return this;
    }

    public void run() {
        wrapper.run();
    }
}
