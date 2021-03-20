package com.networkmodule.config;

import android.app.Application;
import android.util.Log;

import com.networkmodule.BuildConfig;

import com.networkmodule.domain.repository.MainRepository;
import com.networkmodule.domain.repository.MainRepositoryImpl;
import com.networkmodule.domain.repository.remote.api.MainService;
import com.networkmodule.module.base.EzetapNetworkProducationViewModelFactory;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class EzetapCommonProducationDI implements DICommon {

    public static final String HEADER_AUTHORIZTION = "Authorization";
    protected static Retrofit singletonRetrofit;
    protected static EzetapNetworkProducationViewModelFactory singletonStarGazeCommonVMFactory;

    /**
     * 2 min request timeout wait
     **/
    protected final long REQUEST_TIME_OUT_S = 180L;

    protected final Application application;

    public EzetapCommonProducationDI(Application application) {
        this.application = application;
    }

    @Override
    public MainRepository provideMainRepository() {
        return new MainRepositoryImpl(provideMainService());
    }

    @Override
    public MainService provideMainService() {

        return provideRetrofit().create(MainService.class);
    }


    @Override
    public OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.readTimeout(REQUEST_TIME_OUT_S, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(REQUEST_TIME_OUT_S, TimeUnit.SECONDS);
        okHttpClientBuilder.connectTimeout(REQUEST_TIME_OUT_S, TimeUnit.SECONDS);


        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public Response intercept(@NotNull Chain chain) throws IOException {
                Request request = chain.request()
                        .newBuilder()
                        .addHeader(HEADER_AUTHORIZTION,
                                "")
                        .build();
                return chain.proceed(request);
            }
        });
        disableSSLCertificateChecking();
        return okHttpClientBuilder.build();
    }

    /**
     * Disables the SSL certificate checking for new instances of {@link HttpsURLConnection} This has been created to
     * aid testing on a local box, not for use on production.
     */
    private static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {

                    @Override
                    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        // not implemented
                    }

                    @Override
                    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                        // not implemented
                    }

                    @Override
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                }
        };

        try {

            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String s, SSLSession sslSession) {
                    return true;
                }

            });
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Application provideApplication() {
        return application;
    }

    @Override
    public Retrofit provideRetrofit() {
        if (singletonRetrofit == null) {
            synchronized (Retrofit.class) {
                singletonRetrofit = new Retrofit.Builder()
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(provideOkHttpClient())
                        .baseUrl(BuildConfig.API_SERVER)
                        .build();
            }
        }
        Log.d("Response", "" + singletonRetrofit.baseUrl());

        return singletonRetrofit;
    }


    @Override
    public EzetapNetworkProducationViewModelFactory provideViewModelFactory() {
        if (singletonStarGazeCommonVMFactory == null) {
            synchronized (EzetapNetworkProducationViewModelFactory.class) {
                singletonStarGazeCommonVMFactory = new EzetapNetworkProducationViewModelFactory(provideApplication());
            }
        }
        return singletonStarGazeCommonVMFactory;
    }

}
