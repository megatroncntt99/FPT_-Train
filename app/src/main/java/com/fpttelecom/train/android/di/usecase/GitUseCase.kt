package com.fpttelecom.train.android.di.usecase

import com.fpttelecom.train.android.api.Repo
import com.fpttelecom.train.android.api.RequestService
import com.fpttelecom.train.android.api.RequestState
import com.fpttelecom.train.android.api.UiState
import com.fpttelecom.train.android.data.model.UserResponse
import com.fpttelecom.train.android.room.UserRoomRepository
import com.fpttelecom.train.android.utils.JSON
import kotlinx.coroutines.flow.flow

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 3:20 PM
 */

class GitUseCase(
    val getListUser: GetListUser,
    val insertListUser: InsertListUser,
    val getListUserRoom: GetListUserRoom
)

class GetListUser(private val requestService: RequestService) {

    operator fun invoke(repo: Repo) = flow<UiState<List<UserResponse>>> {
        requestService.work(
            onSuccess = {
                emit(
                    UiState(
                        RequestState.SUCCESS,
                        JSON.decodeToList(it.value.data(), Array<UserResponse>::class.java)
                    )
                )
            },
            onError = {
                emit(UiState(RequestState.ERROR, message = it.message))
            }
        ).request(repo)
    }
}

class InsertListUser(private val userRoomRepository: UserRoomRepository) {
    operator fun invoke(list: List<UserResponse>) = flow {
        emit(userRoomRepository.insertList(list))
    }
}

class GetListUserRoom(private val userRoomRepository: UserRoomRepository) {
    operator fun invoke() = flow {
        kotlinx.coroutines.delay(2000)
        emit(userRoomRepository.getListUser())
    }
}