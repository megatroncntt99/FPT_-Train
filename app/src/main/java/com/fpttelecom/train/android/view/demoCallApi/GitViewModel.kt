package com.fpttelecom.train.android.view.demoCallApi

import com.fpttelecom.train.android.api.RequestState
import com.fpttelecom.train.android.api.UiState
import com.fpttelecom.train.android.base.BaseViewModel
import com.fpttelecom.train.android.data.model.UserResponse
import com.fpttelecom.train.android.di.usecase.GitUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import javax.inject.Inject

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 3:29 PM
 */
@HiltViewModel
class GitViewModel @Inject constructor(private val gitUseCase: GitUseCase) :
    BaseViewModel<GitRepo>() {

    private val _uiStateListUser = MutableStateFlow(UiState<List<UserResponse>>())
    val uiStateListUser: StateFlow<UiState<List<UserResponse>>> = _uiStateListUser
    private val _uiMessage = MutableStateFlow("Ban đầu")
    val uiMessage: StateFlow<String> = _uiMessage

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getListUserGit() = viewModelScope {
        gitUseCase.getListUser(repo.repoGetListUser()).onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.catch {
            _uiStateListUser.value = UiState(RequestState.ERROR, message = it.message)
        }.collect {
            it.result?.let { it1 ->
                insertListUser(it1)
            }
        }
    }

    private fun insertListUser(list: List<UserResponse>) = viewModelScope {
        gitUseCase.insertListUser(list).collect {
            _uiMessage.value = "Thành công"
        }
    }

    fun getListUserRoomDB() = viewModelScope {
        gitUseCase.getListUserRoom().onStart {
            _isLoading.value = true
        }.onCompletion {
            _isLoading.value = false
        }.catch {
            _uiStateListUser.value = UiState(RequestState.ERROR, message = it.message)
        }.collect {
            _uiStateListUser.value = UiState(RequestState.SUCCESS, result = it)
        }
    }
}