package com.networkmodule.module.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.networkmodule.domain.RepositoryResponse
import com.networkmodule.domain.model.ResponseData
import com.networkmodule.domain.repository.MainRepository
import com.networkmodule.module.base.EzetapNetworkProducationViewModel
import com.networkmodule.module.base.SPViewModelResponse
import okhttp3.ResponseBody
import java.util.*

class MainViewModel(application: Application) :
    EzetapNetworkProducationViewModel(application) {
    var repository: MainRepository

    fun getCustomUIData(): LiveData<SPViewModelResponse<ResponseData>> {
        return Transformations.map<RepositoryResponse<ResponseData>,
                SPViewModelResponse<ResponseData>>(
            repository!!
                .customUI
        ) { repoResponse ->

            if (repoResponse.success) {
                return@map SPViewModelResponse(repoResponse.repositoryResponse)
            }
            SPViewModelResponse(repoResponse.failureMessage, repoResponse.code)
        }
    }

    fun getImage(): LiveData<SPViewModelResponse<ResponseBody>> {
        return Transformations.map<RepositoryResponse<ResponseBody>,
                SPViewModelResponse<ResponseBody>>(
            repository!!
                .image
        ) { repoResponse ->

            if (repoResponse.success) {
                return@map SPViewModelResponse(repoResponse.repositoryResponse)
            }
            SPViewModelResponse(repoResponse.failureMessage, repoResponse.code)
        }
    }


    init {
        repository = diCommon.provideMainRepository()
    }
}
