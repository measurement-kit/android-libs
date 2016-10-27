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
    private Integer testId;
    private LocalBroadcastManager lbm = null;

    // List of callback to be setted before calling the run method
    LogCallback logDelegate = null;
    EntryCallback entryDelegate = null;

    public OoniTestBase(String test_name, Context context) {
        wrapper = new OoniTestWrapper(test_name);

        lbm = LocalBroadcastManager.getInstance(context);
        // XXX Define a way to give unique id to the tests
        testId = (test_name + new Date().getTime()).hashCode();
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
        this.logDelegate = delegate;
        return this;
    }

    public OoniTestBase on_entry(EntryCallback delegate) {
        this.entryDelegate = delegate;
        return this;
    }



    public OoniTestBase set_options(String key, String value) {
        wrapper.set_options(key, value);
        return this;
    }

    public Integer run(TestCompleteCallback callback) {
        this.run_impl(callback);
        return testId;
    }

    public Integer run() {
        // XXX: it's actually not safe to use a blocking callback on Android
        // If the UI Thread is not responding for more than 3 seconds the system kills the app
        this.run_impl(null);
        return testId;
    }

    private void run_impl(final TestCompleteCallback delegate) {
        wrapper.on_log(new LogCallback() {
            @Override
            public void callback(long verbosity, String message) {
                Intent intent = new Intent();
                intent.setAction("on_log/id/"+testId);
                intent.putExtra("verbosity", verbosity);
                intent.putExtra("message", message);
                lbm.sendBroadcast(intent);
                if (logDelegate != null) {
                    logDelegate.callback(verbosity, message);
                }
            }
        });
        wrapper.on_entry(new EntryCallback() {
            @Override
            public void callback(String entry) {
                Intent intent = new Intent();
                intent.setAction("on_entry/id/"+testId);
                intent.putExtra("entry", entry);
                lbm.sendBroadcast(intent);
                if (entryDelegate != null) {
                    entryDelegate.callback(entry);
                }
            }
        });
        wrapper.run(new TestCompleteCallback() {
            @Override
            public void callback() {
                Intent intent = new Intent();
                intent.setAction("on_end/id/"+testId);
                lbm.sendBroadcast(intent);
                if (delegate != null) {
                    delegate.callback();
                }
            }
        });
    }

}
