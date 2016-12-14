// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.android;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import org.openobservatory.measurement_kit.common.LogCallback;
import org.openobservatory.measurement_kit.nettests.EntryCallback;
import org.openobservatory.measurement_kit.nettests.TestCompleteCallback;

public class TestListener {
    private EntryCallback callback_entry;
    private LogCallback callback_log;
    private TestCompleteCallback callback_test_complete;
    private LocalBroadcastManager manager;
    private BroadcastReceiver receiver_for_entry;
    private BroadcastReceiver receiver_for_log;
    private BroadcastReceiver receiver_for_test_complete;
    private String testId;

    public TestListener(LocalBroadcastManager inst) {
        manager = inst;
    }

    public TestListener on_entry(EntryCallback cb) {
        callback_entry = cb;
        return this;
    }

    public TestListener on_log(LogCallback cb) {
        callback_log = cb;
        return this;
    }

    public TestListener on_test_complete(TestCompleteCallback cb) {
        callback_test_complete = cb;
        return this;
    }

    public void start(String tid) {
        testId = tid;
        on_resume();
    }

    private IntentFilter make_intent_filter(String event) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(testId + event);
        return filter;
    }

    private void listen_for_entry() {
        receiver_for_entry = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (callback_entry != null) {
                    callback_entry.callback(intent.getStringExtra("entry"));
                }
            }
        };
        manager.registerReceiver(receiver_for_entry,
            make_intent_filter("/on_entry"));
    }

    private void listen_for_log() {
        receiver_for_log = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long verbosity = intent.getLongExtra("verbosity", 0);
                String message = intent.getStringExtra("message");
                if (callback_log != null) {
                    callback_log.callback(verbosity, message);
                }
            }
        };
        manager.registerReceiver(receiver_for_log,
            make_intent_filter("/on_log"));
    }

    private void listen_for_test_complete() {
        receiver_for_test_complete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (callback_test_complete != null) {
                    callback_test_complete.callback();
                }
                // Stop listening when we reach end of test
                on_pause();
            }
        };
        manager.registerReceiver(receiver_for_test_complete,
            make_intent_filter("/on_test_complete"));
    }

    public void on_resume() {
        on_pause();
        listen_for_entry();
        listen_for_log();
        listen_for_test_complete();
    }

    private TestListener clear_on_log() {
        if (receiver_for_log != null) {
            manager.unregisterReceiver(receiver_for_log);
        }
        receiver_for_log = null;
        return this;
    }

    private TestListener clear_on_entry() {
        if (receiver_for_entry != null) {
            manager.unregisterReceiver(receiver_for_entry);
        }
        receiver_for_entry = null;
        return this;
    }

    private TestListener clear_on_test_complete() {
        if (receiver_for_test_complete != null) {
            manager.unregisterReceiver(receiver_for_test_complete);
        }
        receiver_for_test_complete = null;
        return this;
    }

    public void on_pause() {
        clear_on_entry();
        clear_on_log();
        clear_on_test_complete();
    }
}
