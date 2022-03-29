package com.fpttelecom.train.android.room

import com.fpttelecom.train.android.data.model.UserResponse

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 4:58 PM
 */

class UserRoomRepository(private val dao: UserDao) {
    suspend fun insertList(list: List<UserResponse>) {
        list.forEach {
            val item = UserEntity(idUser = it.id, login = it.login, avatar_url = it.avatar_url)
            dao.insertUser(item)
        }
    }

    suspend fun getListUser(): List<UserResponse> {
        val listUser = arrayListOf<UserResponse>()
        dao.gelAllUsers().forEach {
            listUser.add(UserResponse(id = it.idUser, avatar_url = it.avatar_url, login = it.login))
        }
        return listUser
    }
}