package com.fpttelecom.train.android.room


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 4:54 PM
 */

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

 companion object {
  const val DATABASE_NAME="user"
 }
 abstract fun userDao() : UserDao
}