package com.fpttelecom.train.android.di.usecase

import com.fpttelecom.train.android.api.Repo
import com.fpttelecom.train.android.api.RequestService
import com.fpttelecom.train.android.api.RequestState
import com.fpttelecom.train.android.api.UiState
import com.fpttelecom.train.android.utils.JSON
import kotlinx.coroutines.flow.flow

class SampleUseCase(val getSample: GetSample) {
}

class GetSample(private val request: RequestService) {

    /*
    * Any là object sample thay thế object muốn parse
    * */
    operator fun invoke(repo: Repo) = flow<UiState<Any>> {
        request.work(
            onSuccess = {
                emit(UiState(RequestState.SUCCESS, JSON.decode(it.value.data(), Any::class.java)))
            },
            onError = {
                emit(UiState(RequestState.ERROR, message = it.message))
            }
        ).request(repo)
    }
}