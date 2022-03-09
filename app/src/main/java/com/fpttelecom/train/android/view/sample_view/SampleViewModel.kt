package com.fpttelecom.train.android.view.sample_view

import androidx.lifecycle.SavedStateHandle
import com.fpttelecom.train.android.api.RequestState
import com.fpttelecom.train.android.api.UiState
import com.fpttelecom.train.android.base.BaseViewModel
import com.fpttelecom.train.android.di.usecase.SampleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SampleViewModel @Inject constructor(
    val request: SampleUseCase,
    private val savedStateHandle: SavedStateHandle
) : BaseViewModel<SampleRepo>() {

    private val _uiStateSample = MutableStateFlow(UiState<Any>())
    val uiStateSample: StateFlow<UiState<Any>> = _uiStateSample

    private var _isLoading = MutableStateFlow(false)
    var isLoading: StateFlow<Boolean> = _isLoading

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getSomething() = viewModelScope {
        request.getSample(repo.repoSample()).onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.catch {
            _uiStateSample.value = UiState(RequestState.ERROR, message = it.message)
        }.collect {
            _uiStateSample.value = it
        }
    }
}