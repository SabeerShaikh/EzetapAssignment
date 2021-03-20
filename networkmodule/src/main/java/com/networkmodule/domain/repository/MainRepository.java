package com.networkmodule.domain.repository;

import android.media.Image;

import androidx.lifecycle.MutableLiveData;

import com.networkmodule.domain.RepositoryResponse;
import com.networkmodule.domain.model.ResponseData;

import okhttp3.ResponseBody;

public interface MainRepository {

    MutableLiveData<RepositoryResponse<ResponseData>> getCustomUI();

    MutableLiveData<RepositoryResponse<ResponseBody>> getImage();

}
