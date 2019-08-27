package io.ooni.mk.androidTest;

import org.junit.Test;

import androidx.test.filters.SmallTest;
import androidx.test.core.app.ApplicationProvider;

import com.google.gson.Gson;

import java.util.ArrayList;

import android.content.Context;
import com.google.common.truth.Truth;
import io.ooni.mk.MKAsyncTask;
import io.ooni.mk.MKResourcesManager;

@SmallTest public class MKWebConnectivityTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        Context context = ApplicationProvider.getApplicationContext();
        boolean okay = MKResourcesManager.maybeUpdateResources(context);
        Truth.assertThat(okay).isTrue();
        MKSettings settings = new MKSettings();
        Gson gson = new Gson();
        settings.name = "WebConnectivity";
        settings.log_level = "INFO";
        settings.options.ca_bundle_path = MKResourcesManager.getCABundlePath(context);
        settings.options.geoip_asn_path = MKResourcesManager.getASNDBPath(context);
        settings.options.geoip_country_path = MKResourcesManager.getCountryDBPath(context);
        settings.inputs = new ArrayList<String>();
        settings.inputs.add("https://www.torproject.org/");
        settings.inputs.add("https://x.org/");
        settings.inputs.add("https://slashdot.org/");
        MKAsyncTask task = MKAsyncTask.start(gson.toJson(settings));
        while (!task.isDone()) {
            System.out.println(task.waitForNextEvent());
        }
    }
}
