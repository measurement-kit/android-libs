// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import com.google.common.truth.Truth;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

import io.ooni.mk.MKAsyncTask;
import io.ooni.mk.MKResourcesManager;

@SmallTest public class MKNDTTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        Context context = ApplicationProvider.getApplicationContext();
        boolean okay = MKResourcesManager.maybeUpdateResources(context);
        Truth.assertThat(okay).isTrue();

        MKTSettings settings = new MKTSettings();
        settings.name = "Ndt";
        settings.log_level = "INFO";
        settings.options.ca_bundle_path = MKResourcesManager.getCABundlePath(context);
        settings.options.geoip_asn_path = MKResourcesManager.getASNDBPath(context);
        settings.options.geoip_country_path = MKResourcesManager.getCountryDBPath(context);
        settings.options.no_file_report = true;

        Gson gson = new Gson();
        MKAsyncTask task = MKAsyncTask.start(gson.toJson(settings));
        Map<String, Integer> keys = new HashMap<String, Integer>();
        while (!task.isDone()) {
            String serializedEvent = task.waitForNextEvent();
            MKTEvent event = gson.fromJson(serializedEvent, MKTEvent.class);
            Integer value = keys.get(event.key);
            if (value == null) {
                value = new Integer(0);
            }
            keys.put(event.key, value + 1);
            System.out.println(serializedEvent);
        }

        for (Map.Entry<String, Integer> e : keys.entrySet()) {
            System.out.println(e.getKey() + ":" + e.getValue().toString());
            Truth.assertThat(e.getKey().startsWith("failure.")).isFalse();
        }

        Truth.assertThat(keys.get("status.measurement_done")).isEqualTo(1);
        Truth.assertThat(keys.get("status.measurement_submission")).isEqualTo(1);
        Truth.assertThat(keys.get("status.update.performance")).isGreaterThan(20);
        Truth.assertThat(keys.get("status.measurement_start")).isEqualTo(1);
        Truth.assertThat(keys.get("status.started")).isEqualTo(1);
        Truth.assertThat(keys.get("status.end")).isEqualTo(1);
        Truth.assertThat(keys.get("status.progress")).isGreaterThan(20);
        Truth.assertThat(keys.get("status.queued")).isEqualTo(1);
        Truth.assertThat(keys.get("log")).isGreaterThan(1);
        Truth.assertThat(keys.get("status.report_create")).isEqualTo(1);
        Truth.assertThat(keys.get("measurement")).isEqualTo(1);
        Truth.assertThat(keys.get("status.geoip_lookup")).isEqualTo(1);
    }
}
