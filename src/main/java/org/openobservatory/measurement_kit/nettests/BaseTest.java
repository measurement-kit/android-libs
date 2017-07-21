// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.

package org.openobservatory.measurement_kit.nettests;

import org.openobservatory.measurement_kit.android.IntentRouter;
import org.openobservatory.measurement_kit.common.LogCallback;

public interface BaseTest {
    BaseTest set_verbosity(long verbosity);

    BaseTest increase_verbosity();

    BaseTest set_input_filepath(String fpath);

    BaseTest set_output_filepath(String fpath);

    BaseTest set_error_filepath(String fpath);

    BaseTest use_logcat();

    BaseTest on_log(LogCallback delegate);

    BaseTest on_log(final String event_id, final IntentRouter router);

    BaseTest on_progress(ProgressCallback delegate);

    BaseTest on_progress(final String event_id, final IntentRouter router);

    BaseTest on_event(EventCallback delegate);

    BaseTest on_event(final String event_id, final IntentRouter router);

    BaseTest on_entry(EntryCallback delegate);

    BaseTest on_entry(final String event_id, final IntentRouter router);

    BaseTest set_options(String key, String value);

    void run();

    void start(TestCompleteCallback cb);

    void start(final String event_id, final IntentRouter router);
}
