package com.fpttelecom.train.android.room

import androidx.room.*

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 4:51 PM
 */

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: UserEntity)

    @Query("Select * from user")
    suspend fun gelAllUsers(): List<UserEntity>

    @Update
    suspend fun updateUser(user: UserEntity)

    @Delete
    suspend fun deleteUser(user: UserEntity)

}