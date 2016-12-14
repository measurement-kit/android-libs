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
    private String testId;

    /*
     * We want to allow the user to unregister callbacks. In order to do
     * that, we could either tell users to manage the `BroadcastReceiver`
     * objects themselves or we could store them and manage them. We
     * chose the latter approach (as you can see :-).
     */
    private BroadcastReceiver onLogReceiver = null;
    private BroadcastReceiver onEntryReceiver = null;
    private BroadcastReceiver onEndReceiver = null;

    public TestListener(String id, LocalBroadcastManager lbm) {
        this.lbm = lbm;
        this.testId = id;
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

    public TestListener on_end(final TestCompleteCallback cb) {
        clear_on_end();
        this.onEndReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                cb.callback();
                // Make sure to remove all the callbacks when the on_end event is called
                clear_all();
            }
        };
        lbm.registerReceiver(onEndReceiver, make_intent_filter("/on_entry"));
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

    public TestListener clear_on_end() {
        if (onEndReceiver != null) {
            lbm.unregisterReceiver(onEndReceiver);
        }
        onEndReceiver = null;
        return this;
    }

    public void clear_all() {
        this.clear_on_log();
        this.clear_on_entry();
        this.clear_on_end();
    }

    private IntentFilter make_intent_filter(String event) {
        IntentFilter filter = new IntentFilter();
        filter.addAction(testId + event);
        return filter;
    }

}
