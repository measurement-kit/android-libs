package io.ooni.mk.androidTest;

import org.junit.Test;

import androidx.test.filters.SmallTest;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.ooni.mk.MKAsyncTask;

@SmallTest public class MKWebConnectivityTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    @Test public void perform() {
        MKSettings settings = new MKSettings();
        Gson gson = new Gson();
        settings.name = "WebConnectivity";
        settings.log_level = "INFO";
        settings.options.ca_bundle_path = "cacert.pem";
        settings.options.geoip_asn_path = "asn.mmdb";
        settings.options.geoip_country_path = "country.mmdb";
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
