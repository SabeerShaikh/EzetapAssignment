package com.networkmodule.config;

import android.app.Application;

import com.networkmodule.domain.repository.MainRepository;
import com.networkmodule.domain.repository.remote.api.MainService;
import com.networkmodule.module.base.EzetapNetworkProducationViewModelFactory;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public interface DICommon {
    /**
     * Repositories
     */
    MainRepository provideMainRepository();
    /** ENDS Repositories Providers */

    /**
     * API Service Providers
     **/

    MainService provideMainService();

    /**
     * ENDS API Service Providers
     **/

    /**
     * ENDS API Service Providers
     **/


    OkHttpClient provideOkHttpClient();

    Application provideApplication();

    Retrofit provideRetrofit();


    EzetapNetworkProducationViewModelFactory provideViewModelFactory();


}
