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
    private LocalBroadcastManager lbm;
    private BroadcastReceiver onEntryReceiver;
    private BroadcastReceiver onLogReceiver;
    private BroadcastReceiver onTestCompleteReceiver;
    private String testId;

    public TestListener(String id, LocalBroadcastManager m) {
        lbm = m;
        testId = id;
    }

    public TestListener on_log(final LogCallback cb) {
        clear_on_log();
        this.onLogReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long verbosity = intent.getLongExtra("verbosity", 0);
                String message = intent.getStringExtra("message");
                cb.callback(verbosity, message);
            }
        };
        lbm.registerReceiver(onLogReceiver, make_intent_filter("/on_log"));
        return this;
    }

    public TestListener on_entry(final EntryCallback cb) {
        clear_on_entry();
        this.onEntryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String entry = intent.getStringExtra("entry");
                cb.callback(entry);
            }
        };
        lbm.registerReceiver(onEntryReceiver, make_intent_filter("/on_entry"));
        return this;
    }

    public TestListener on_test_complete(final TestCompleteCallback cb) {
        clear_on_test_complete();
        this.onTestCompleteReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                // Make sure to remove all the callbacks when the
                // on_test_complete event is called
                clear_all();
                cb.callback();
            }
        };
        lbm.registerReceiver(onTestCompleteReceiver,
                make_intent_filter("/on_test_complete"));
        return this;
    }

    public TestListener clear_on_log() {
        if (onLogReceiver != null) {
            lbm.unregisterReceiver(onLogReceiver);
        }
        onLogReceiver = null;
        return this;
    }

    public TestListener clear_on_entry() {
        if (onEntryReceiver != null) {
            lbm.unregisterReceiver(onEntryReceiver);
        }
        onEntryReceiver = null;
        return this;
    }

    public TestListener clear_on_test_complete() {
        if (onTestCompleteReceiver != null) {
            lbm.unregisterReceiver(onTestCompleteReceiver);
        }
        onTestCompleteReceiver = null;
        return this;
    }

    public void clear_all() {
        this.clear_on_entry();
        this.clear_on_log();
        this.clear_on_test_complete();
    }

    private IntentFilter make_intent_filter(String event) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(testId + event);
        return filter;
    }
}
