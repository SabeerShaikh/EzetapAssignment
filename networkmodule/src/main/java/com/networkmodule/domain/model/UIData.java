package com.networkmodule.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UIData implements Serializable {
    @SerializedName("uitype")
    public String uitype;
    @SerializedName("value")
    public String value;
    @SerializedName("key")
    public String key;
    @SerializedName("hint")
    public String hint;

}
