package com.networkmodule.domain.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseData implements Serializable {
    @SerializedName("heading-text")
    public String name;
    @SerializedName("uidata")
    public List<UIData> uidata;

}
