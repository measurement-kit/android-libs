// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.android;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.openobservatory.measurement_kit.common.LogCallback;
import org.openobservatory.measurement_kit.nettests.EntryCallback;
import org.openobservatory.measurement_kit.nettests.OoniTestBase;
import org.openobservatory.measurement_kit.nettests.TestCompleteCallback;

public class TestRunner<T> {
    private OoniTestBase testBase;
    private String testId;

    public String getTestId() {
        return testId;
    }

    public TestRunner(String id, OoniTestBase test) {
        testId = id;
        testBase = test;
    }

    public TestRunner<T> set_verbosity(long verbosity) {
        testBase.set_verbosity(verbosity);
        return this;
    }

    public TestRunner<T> increase_verbosity() {
        testBase.increase_verbosity();
        return this;
    }

    public TestRunner<T> set_input_filepath(String fpath) {
        testBase.set_input_filepath(fpath);
        return this;
    }

    public TestRunner<T> set_output_filepath(String fpath) {
        testBase.set_output_filepath(fpath);
        return this;
    }

    public TestRunner<T> set_error_filepath(String fpath) {
        testBase.set_error_filepath(fpath);
        return this;
    }

    public TestRunner<T> set_options(String key, String value) {
        testBase.set_options(key, value);
        return this;
    }

    public void start(final LocalBroadcastManager lbm) {
        testBase.on_log(new LogCallback() {
            @Override
            public void callback(long verbosity, String message) {
                Intent intent = new Intent();
                intent.setAction(testId + "/on_log");
                intent.putExtra("verbosity", verbosity);
                intent.putExtra("message", message);
                lbm.sendBroadcast(intent);
            }
        });
        testBase.on_entry(new EntryCallback() {
            @Override
            public void callback(String entry) {
                Intent intent = new Intent();
                intent.setAction(testId + "/on_entry");
                intent.putExtra("entry", entry);
                lbm.sendBroadcast(intent);
            }
        });
        testBase.start(new TestCompleteCallback() {
            @Override
            public void callback() {
                Intent intent = new Intent();
                intent.setAction(testId + "/on_test_complete");
                lbm.sendBroadcast(intent);
            }
        });
    }
}
