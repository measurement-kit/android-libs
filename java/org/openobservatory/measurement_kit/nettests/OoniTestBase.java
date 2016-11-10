// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.nettests;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.openobservatory.measurement_kit.android.DnsUtils;
import org.openobservatory.measurement_kit.common.LogCallback;
import org.openobservatory.measurement_kit.swig.OoniTestWrapper;

import java.util.Date;

public class OoniTestBase {

    private OoniTestWrapper wrapper = null;

    public OoniTestBase(String test_name) {
        wrapper = new OoniTestWrapper(test_name);

        // Rationale: start with reasonable DNS configuration and then the user is
        // free to override it using calling again `set_options` if need be.
        set_options("dns/nameserver", DnsUtils.get_device_dns());
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

    public OoniTestBase on_entry(EntryCallback delegate) {
        wrapper.on_entry(delegate);
        return this;
    }

    public OoniTestBase set_options(String key, String value) {
        wrapper.set_options(key, value);
        return this;
    }

    public void run() {
        wrapper.run();
    }

    public void run(TestCompleteCallback callback) {
        wrapper.run(callback);
    }

    public Integer run(final Context ctx) {
        // XXX if you use this method the callbacks setted with on_log and
        //     on entry will be overridden
        final LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(ctx);
        // XXX define a way to have a reliable id
        Integer testId = (test_name + new Date().getTime()).hashCode();
        wrapper.on_log(new LogCallback() {
            @Override
            public void callback(long verbosity, String message) {
                Intent intent = new Intent();
                intent.setAction(testId + "/on_log");
                intent.putExtra("verbosity", verbosity);
                intent.putExtra("message", message);
                lbm.sendBroadcast(intent);
            }
        });
        wrapper.on_entry(new EntryCallback() {
            @Override
            public void callback(String entry) {
                Intent intent = new Intent();
                intent.setAction(testId + "/on_entry");
                intent.putExtra("entry", entry);
                lbm.sendBroadcast(intent);
            }
        });
        wrapper.run(new TestCompleteCallback() {
            @Override
            public void callback() {
                Intent intent = new Intent();
                intent.setAction(testId + "/on_end");
                lbm.sendBroadcast(intent);
            }
        });
        return testId;
    }
}
