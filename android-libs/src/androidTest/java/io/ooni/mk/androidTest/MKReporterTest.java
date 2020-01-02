// Part of measurement-kit <https://measurement-kit.github.io/>.
// Measurement-kit is free software. See AUTHORS and LICENSE for more
// information on the copying conditions.
package io.ooni.mk.androidTest;

import android.content.Context;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import com.google.common.truth.Truth;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import org.junit.Test;

import io.ooni.mk.MKReporterResults;
import io.ooni.mk.MKReporterTask;
import io.ooni.mk.MKResourcesManager;

@SmallTest public class MKReporterTest {
    static {
        System.loadLibrary("measurement_kit");
    }

    static final String softwareName = "android-libs";
    static final String softwareVersion = "0.1.0";
    static final String testName = "dummy";
    static final String testVersion = "0.1.0";
    static final Gson gson = new Gson();

    private MKReporterTask initialize() {
        Context context = ApplicationProvider.getApplicationContext();
        boolean okay = MKResourcesManager.maybeUpdateResources(context);
        Truth.assertThat(okay).isTrue();
        return new MKReporterTask(softwareName, softwareVersion,
                MKResourcesManager.getCABundlePath(context));
    }

    private MKReporterResults submit(MKReporterTask task, MKTMeasurement measurement, Stats expect) {
        final long timeout = 300;
        MKReporterResults results = task.maybeDiscoverAndSubmit(
            gson.toJson(measurement), timeout
        );
        System.out.print(results.getLogs());
        System.out.println("updatedMeasurement  : " + results.getUpdatedSerializedMeasurement());
        System.out.println("updatedReportID     : " + results.getUpdatedReportID());
        System.out.println("serializedStats     : " + results.getSerializedStats());
        System.out.println("reason              : " + results.getReason());
        Truth.assertThat(results.isGood()).isTrue();
        Truth.assertThat(results.getUpdatedReportID().length()).isGreaterThan(0);
        Stats real = gson.fromJson(results.getSerializedStats(), Stats.class);
        Truth.assertThat(real.equals(expect)).isTrue();
        return results;
    }

    @Test public void submitOne() {
        Stats expect = new Stats();
        expect.bouncer_okay = 1;
        expect.load_request_okay = 1;
        expect.open_report_okay = 1;
        expect.update_report_okay = 1;
        submit(initialize(), new MKTMeasurement(
            testName, testVersion, "", ""
        ), expect);
    }

    @Test public void submitMany() {
        MKReporterTask task = initialize();

        Stats expect = new Stats();
        expect.bouncer_okay = 1;
        expect.load_request_okay = 1;
        expect.open_report_okay = 1;
        expect.update_report_okay = 1;
        submit(task, new MKTMeasurement(
            testName, testVersion, "a", ""
        ), expect);

        expect = new Stats();
        expect.load_request_okay = 1;
        expect.update_report_okay = 1;
        submit(task, new MKTMeasurement(
            testName, testVersion, "b", ""
        ), expect);

        expect = new Stats();
        expect.close_report_okay = 1;
        expect.load_request_okay = 1;
        expect.open_report_okay = 1;
        expect.update_report_okay = 1;
        submit(task, new MKTMeasurement(
            testName + "x", testVersion, "c", ""
        ), expect);
    }

    private class Stats {
        @SerializedName("bouncer_error")
        public long bouncer_error = 0;

        @SerializedName("bouncer_no_collectors")
        public long bouncer_no_collectors = 0;

        @SerializedName("bouncer_okay")
        public long bouncer_okay = 0;

        @SerializedName("close_report_error")
        public long close_report_error = 0;

        @SerializedName("close_report_okay")
        public long close_report_okay = 0;

        @SerializedName("load_request_error")
        public long load_request_error = 0;

        @SerializedName("load_request_okay")
        public long load_request_okay = 0;

        @SerializedName("open_report_error")
        public long open_report_error = 0;

        @SerializedName("open_report_okay")
        public long open_report_okay = 0;

        @SerializedName("report_id_empty")
        public long report_id_empty = 0;

        @SerializedName("serialize_measurement_error")
        public long serialize_measurement_error = 0;

        @SerializedName("update_report_error")
        public long update_report_error = 0;

        @SerializedName("update_report_okay")
        public long update_report_okay = 0;

        public boolean equals(Stats other) {
            if (bouncer_error != other.bouncer_error) {
                return false;
            }
            if (bouncer_no_collectors != other.bouncer_no_collectors) {
                return false;
            }
            if (bouncer_okay != other.bouncer_okay) {
                return false;
            }
            if (close_report_error != other.close_report_error) {
                return false;
            }
            if (close_report_okay != other.close_report_okay) {
                return false;
            }
            if (load_request_error != other.load_request_error) {
                return false;
            }
            if (load_request_okay != other.load_request_okay) {
                return false;
            }
            if (open_report_error != other.open_report_error) {
                return false;
            }
            if (open_report_okay != other.open_report_okay) {
                return false;
            }
            if (report_id_empty != other.report_id_empty) {
                return false;
            }
            if (serialize_measurement_error != other.serialize_measurement_error) {
                return false;
            }
            if (update_report_error != other.update_report_error) {
                return false;
            }
            if (update_report_okay != other.update_report_okay) {
                return false;
            }
            return true;
        }
    }
}
