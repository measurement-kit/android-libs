package io.ooni.mk.androidTest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class MKSettings {
    @SerializedName("inputs")
    public List<String> inputs;

    @SerializedName("log_level")
    public String log_level;

    @SerializedName("name")
    public String name;

    @SerializedName("options")
    public Options options = new Options();

    static class Options {
        @SerializedName("net/ca_bundle_path")
        public String ca_bundle_path;

        @SerializedName("geoip_asn_path")
        public String geoip_asn_path;

        @SerializedName("geoip_country_path")
        public String geoip_country_path;
    }
}
