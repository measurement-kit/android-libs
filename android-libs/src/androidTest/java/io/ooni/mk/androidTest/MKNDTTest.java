// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import com.google.common.truth.Truth;
import com.google.gson.Gson;
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

        MKSettings settings = new MKSettings();
        settings.name = "Ndt";
        settings.log_level = "INFO";
        settings.options.ca_bundle_path = MKResourcesManager.getCABundlePath(context);
        settings.options.geoip_asn_path = MKResourcesManager.getASNDBPath(context);
        settings.options.geoip_country_path = MKResourcesManager.getCountryDBPath(context);

        MKAsyncTask task = MKAsyncTask.start(new Gson().toJson(settings));
        while (!task.isDone()) {
            System.out.println(task.waitForNextEvent());
        }
    }
}
