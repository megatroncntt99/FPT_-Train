package com.fpttelecom.train.android.view.mviDemo

import com.fpttelecom.train.android.api.Repo
import com.fpttelecom.train.android.api.RequestState
import com.fpttelecom.train.android.api.UiState
import com.fpttelecom.train.android.base.BaseViewModel
import com.fpttelecom.train.android.data.model.UserResponse
import com.fpttelecom.train.android.di.usecase.GitUseCase
import com.fpttelecom.train.android.view.demoCallApi.GitRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Creator: Nguyen Van Van
 * Date: 28,April,2022
 * Time: 9:14 AM
 */
@OptIn(InternalCoroutinesApi::class)
@HiltViewModel
class MVIViewModel @Inject constructor(val useCase: GitUseCase) : BaseViewModel<GitRepo>() {

    val userIntent = Channel<MainIntent>(capacity = Channel.UNLIMITED)
    private val _state = MutableStateFlow(UiState<List<UserResponse>>())
    val state: StateFlow<UiState<List<UserResponse>>>
        get() = _state

    init {
        handleIntent()
    }


    private fun handleIntent() {
        viewModelScope {
            userIntent.consumeEach {
                when (it) {
                    MainIntent.FetchUser -> {
                        fetchUser()
                    }
                }
            }
        }

    }

    private fun fetchUser() {
        viewModelScope {
            useCase.getListUser(repo.repoGetListUser()).onStart {
                //show loading
            }.catch {
                _state.value = UiState(RequestState.ERROR, message = it.message)
            }.onCompletion {
                //hide loading
            }.collect {
                _state.value = UiState(RequestState.SUCCESS, result = it.result)
            }
        }
    }

}