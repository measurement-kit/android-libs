define(MK_DECLARE_TEST, `
// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.nettests;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;

import org.openobservatory.measurement_kit.common.LogCallback;

public class $1 {
    private org.openobservatory.measurement_kit.swig.$1 wrapper
        = new org.openobservatory.measurement_kit.swig.$1();

    public $1 set_verbosity(long verbosity) {
        wrapper.set_verbosity(verbosity);
        return this;
    }

    public $1 increase_verbosity() {
        wrapper.increase_verbosity();
        return this;
    }

    public $1 set_input_filepath(String fpath) {
        wrapper.set_input_filepath(fpath);
        return this;
    }

    public $1 set_output_filepath(String fpath) {
        wrapper.set_output_filepath(fpath);
        return this;
    }

    public $1 set_error_filepath(String fpath) {
        wrapper.set_error_filepath(fpath);
        return this;
    }

    public $1 use_logcat() {
        wrapper.use_logcat();
        return this;
    }

    public $1 on_log(LogCallback delegate) {
        wrapper.on_log(delegate);
        return this;
    }

    public $1 on_log(final String event_id,
                           final LocalBroadcastManager manager) {
        wrapper.on_log(new LogCallback() {
            @Override
            public void callback(long verbosity, String message) {
                Intent intent = new Intent();
                intent.setAction(event_id);
                intent.putExtra("type", "on_log");
                intent.putExtra("verbosity", verbosity);
                intent.putExtra("message", message);
                manager.sendBroadcast(intent);
            }
        });
        return this;
    }

    public $1 on_progress(ProgressCallback delegate) {
        wrapper.on_progress(delegate);
        return this;
    }

    public $1 on_progress(final String event_id,
                                final LocalBroadcastManager manager) {
        wrapper.on_progress(new ProgressCallback() {
            @Override
            public void callback(double percent, String msg) {
                Intent intent = new Intent();
                intent.setAction(event_id);
                intent.putExtra("type", "on_progress");
                intent.putExtra("percent", percent);
                intent.putExtra("message", msg);
                manager.sendBroadcast(intent);
            }
        });
        return this;
    }

    public $1 on_event(EventCallback delegate) {
        wrapper.on_event(delegate);
        return this;
    }

    public $1 on_event(final String event_id,
                             final LocalBroadcastManager manager) {
        wrapper.on_event(new EventCallback() {
            @Override
            public void callback(String msg) {
                Intent intent = new Intent();
                intent.setAction(event_id);
                intent.putExtra("type", "on_event");
                intent.putExtra("message", msg);
                manager.sendBroadcast(intent);
            }
        });
        return this;
    }

    public $1 on_entry(EntryCallback delegate) {
        wrapper.on_entry(delegate);
        return this;
    }

    public $1 on_entry(final String event_id,
                             final LocalBroadcastManager manager) {
        wrapper.on_entry(new EntryCallback() {
            @Override
            public void callback(String entry) {
                Intent intent = new Intent();
                intent.setAction(event_id);
                intent.putExtra("type", "on_entry");
                intent.putExtra("entry", entry);
                manager.sendBroadcast(intent);
            }
        });
        return this;
    }

    public $1 set_options(String key, String value) {
        wrapper.set_options(key, value);
        return this;
    }

    public void run() {
        wrapper.run();
    }

    public void start(TestCompleteCallback cb) {
        wrapper.start(cb);
    }

    public void start(final String event_id,
                      final LocalBroadcastManager manager) {
        wrapper.start(new TestCompleteCallback() {
            @Override
            public void callback() {
                Intent intent = new Intent();
                intent.setAction(event_id);
                intent.putExtra("type", "on_test_complete");
                manager.sendBroadcast(intent);
            }
        });
    }
}
')

MK_DECLARE_TEST(MK_TEST_NAME)
