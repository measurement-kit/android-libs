package io.ooni.mk.androidTest;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class MKTMeasurement {
    public MKTMeasurement(String test_name, String test_version,
                          String input, String failure) {
        this.input = input;
        this.test_keys.failure = failure;
        this.test_name = test_name;
        this.test_version = test_version;
    }

    @SerializedName("data_format_version")
    public String data_format_version = "0.2.0";

    @SerializedName("id")
    public String id = "9858087b-15b9-4913-a87e-2520f85393b2";

    @SerializedName("input")
    public String input;

    @SerializedName("input_hashes")
    public List<String> input_hashes = new ArrayList<String>();

    @SerializedName("measurement_start_time")
    public String measurement_start_time = "2019-07-26 10:39:09";

    @SerializedName("options")
    public List<String> options = new ArrayList<String>();

    @SerializedName("probe_asn")
    public String probe_asn = "AS0";

    @SerializedName("probe_cc")
    public String probe_cc = "ZZ";

    @SerializedName("probe_ip")
    public String probe_ip = "127.0.0.1";

    @SerializedName("report_id")
    public String report_id = "";

    @SerializedName("software_name")
    public String software_name = "android-libs";

    @SerializedName("software_version")
    public String software_version = "0.0.1";

    @SerializedName("test_helpers")
    public Map<String, String> test_helpers = new HashMap<String, String>();

    @SerializedName("test_keys")
    public TestKeys test_keys = new TestKeys();

    @SerializedName("test_name")
    public String test_name = "";

    @SerializedName("test_runtime")
    public double test_runtime = 0.07317614555358887;

    @SerializedName("test_start_time")
    public String test_start_time = "2019-07-26 10:39:05";

    @SerializedName("test_version")
    public String test_version = "";

    static class TestKeys {
        @SerializedName("failure")
        public String failure = "";
    }
}
