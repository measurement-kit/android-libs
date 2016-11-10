// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.nettests;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;

import org.openobservatory.measurement_kit.common.LogCallback;

public class TestListener {

    private LocalBroadcastManager lbm;
    private Integer testId;

    // When we register a BroadcastReveiver we need to unregister it
    // when we don't need it more (like when we close the activity).
    // To handle this case in a safer way I save the receiver created
    // without giving it back to the caller, so it's my responsibility
    // to keep track of it, but it remain the responsibility of the
    // developer to call clear to unregister the listener

    private BroadcastReceiver onLogReceiver = null;
    private BroadcastReceiver onEntryReceiver = null;
    private BroadcastReceiver onEndReceiver = null;

    public TestListener(Integer id, Context context) {
        lbm = LocalBroadcastManager.getInstance(context);
        this.testId = id;
    }

    public TestListener on_log(final LogCallback cb) {
        clear_on_log();
        IntentFilter filter = new IntentFilter();
        filter.addAction(testId + "/on_log");
        this.onLogReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                long verbosity = intent.getLongExtra("verbosity", 0);
                String message = intent.getStringExtra("message");
                cb.callback(verbosity, message);
            }
        };
        lbm.registerReceiver(onLogReceiver, filter);
        return this;
    }

    public TestListener on_entry(final EntryCallback cb) {
        clear_on_entry();
        IntentFilter filter = new IntentFilter();
        filter.addAction(testId + "/on_entry");
        this.onEntryReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String entry = intent.getStringExtra("entry");
                cb.callback(entry);
            }
        };
        lbm.registerReceiver(onEntryReceiver, filter);
        return this;
    }

    public TestListener on_end(final TestCompleteCallback cb) {
        clear_on_end();
        IntentFilter filter = new IntentFilter();
        filter.addAction(testId + "/on_end");
        this.onEndReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                cb.callback();
                // Make sure to remove all the callbacks when the on_end event is called
                clear_all();
            }
        };
        lbm.registerReceiver(onEndReceiver, filter);
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

}
