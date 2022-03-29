package com.fpttelecom.train.android.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 4:52 PM
 */

@Entity(tableName = "user")
data class UserEntity(@PrimaryKey(autoGenerate = true)var id: Int? = null, val idUser:Int ,val login: String, var avatar_url: String)