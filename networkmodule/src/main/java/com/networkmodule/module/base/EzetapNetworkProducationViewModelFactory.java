package com.networkmodule.module.base;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


import com.networkmodule.module.ui.viewmodel.MainViewModel;

import org.jetbrains.annotations.NotNull;


public class EzetapNetworkProducationViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private Application mApplication;

    public EzetapNetworkProducationViewModelFactory(Application application) {
        mApplication = application;
    }

    @Override
    public <T extends ViewModel> T create(@NotNull Class<T> modelClass) {

        if (modelClass == MainViewModel.class) {
            return (T) new MainViewModel(mApplication);
        }

        return super.create(modelClass);
    }
}
