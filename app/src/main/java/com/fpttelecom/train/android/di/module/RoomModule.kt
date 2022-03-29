package com.fpttelecom.train.android.di.module

import android.app.Application
import androidx.room.Room
import com.fpttelecom.train.android.room.AppDatabase
import com.fpttelecom.train.android.room.UserRoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun userRoomRepository(db: AppDatabase): UserRoomRepository {
        return UserRoomRepository(db.userDao())
    }
}