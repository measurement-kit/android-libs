package io.ooni.mk.androidTest;

import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

class MKTEvent {
    @SerializedName("key") public String key;
    @SerializedName("value") public JsonObject value;
}
