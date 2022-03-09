package com.fpttelecom.train.android.di.module

import com.fpttelecom.train.android.api.RequestService
import com.fpttelecom.train.android.di.usecase.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
}