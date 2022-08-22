package com.fpttelecom.train.android.di.module

import com.fpttelecom.train.android.api.RequestService
import com.fpttelecom.train.android.di.usecase.*
import com.fpttelecom.train.android.room.UserRoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideSampleUseCase(requestService: RequestService): SampleUseCase {
        return SampleUseCase(getSample = GetSample(requestService))
    }

    @Provides
    fun provideHomeUseCase(requestService: RequestService): HomeUseCase {
        return HomeUseCase(
            getNotifications = GetNotifications(requestService),
            getNews = GetNews(requestService),
            getBook = GetBook(requestService)
        )
    }

    @Provides
    fun provideGitUseCase(
        requestService: RequestService,
        userRoomRepository: UserRoomRepository
    ): GitUseCase {
        return GitUseCase(
            getListUser = GetListUser(requestService),
            insertListUser = InsertListUser(userRoomRepository),
            getListUserRoom = GetListUserRoom(userRoomRepository)
        )
    }
}