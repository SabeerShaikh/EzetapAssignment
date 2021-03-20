package com.networkmodule.domain.repository;

import okhttp3.ResponseBody;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;


import com.networkmodule.domain.RepositoryResponse;
import com.networkmodule.domain.model.ResponseData;
import com.networkmodule.domain.repository.remote.api.MainService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainRepositoryImpl implements MainRepository {

    private MainService service;

    public MainRepositoryImpl(MainService fileService) {
        this.service = fileService;
    }

    @Override
    public MutableLiveData<RepositoryResponse<ResponseData>> getCustomUI() {
        MutableLiveData<RepositoryResponse<ResponseData>> result = new MutableLiveData<>();
        service.fetchCustomUI().enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                Log.d("DataDailyForcast111", "" + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(new RepositoryResponse<>(response.body()));

                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                Log.d("DataDailyForcast", "" + t.getMessage());
                Log.d("DataDailyForcast111", "" + call.request().url());
                result.setValue(new RepositoryResponse<ResponseData>(t.getMessage(),
                        400));
            }

        });
        return result;
    }

    @Override
    public MutableLiveData<RepositoryResponse<ResponseBody>> getImage() {
        MutableLiveData<RepositoryResponse<ResponseBody>> result = new MutableLiveData<>();

        service.fetchImage().enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d("DataDailyForcast111", "" + response.body());
                if (response.isSuccessful() && response.body() != null) {
                    result.setValue(new RepositoryResponse<>(response.body()));

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("DataDailyForcast", "" + t.getMessage());
                Log.d("DataDailyForcast111", "" + call.request().url());
                result.setValue(new RepositoryResponse<ResponseBody>(t.getMessage(),
                        400));

            }

        });
        return result;
    }


}
