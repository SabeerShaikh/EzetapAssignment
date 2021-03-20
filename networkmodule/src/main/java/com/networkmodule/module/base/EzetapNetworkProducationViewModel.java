package com.networkmodule.module.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.networkmodule.config.DICommon;
import com.networkmodule.config.EzetapCommonProducationDI;

public class EzetapNetworkProducationViewModel extends AndroidViewModel {

    protected static DICommon diCommon;
    protected static EzetapNetworkProducationViewModelFactory vmCommonFactory;
    protected Application application;

    public EzetapNetworkProducationViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        if (diCommon == null) {
            diCommon = new EzetapCommonProducationDI(application);
            vmCommonFactory = diCommon.provideViewModelFactory();
        }
    }
}
