// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import org.junit.Test;
import androidx.test.filters.SmallTest;
import com.google.gson.Gson;
import io.ooni.mk.MKAsyncTask;

@SmallTest public class MKNDTTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        MKSettings settings = new MKSettings();
        Gson gson = new Gson();
        settings.name = "Ndt";
        settings.log_level = "INFO";
        settings.options.ca_bundle_path = "cacert.pem";
        settings.options.geoip_asn_path = "asn.mmdb";
        settings.options.geoip_country_path = "country.mmdb";
        MKAsyncTask task = MKAsyncTask.start(gson.toJson(settings));
        while (!task.isDone()) {
            System.out.println(task.waitForNextEvent());
        }
    }
}
