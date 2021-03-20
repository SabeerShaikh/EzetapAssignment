package com.networkmodule.domain.repository.remote.api;

import android.media.Image;

import com.enfecassignment.domain.repository.remote.APIService;
import com.networkmodule.domain.model.ResponseData;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface MainService extends APIService {

    @POST("mobileapps/android_assignment.json")
    Call<ResponseData> fetchCustomUI();

    @GET("portal/images/logo.gif")
    Call<ResponseBody> fetchImage();
}
