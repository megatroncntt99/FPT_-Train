package com.fpttelecom.train.android.di.usecase

import com.fpttelecom.train.android.api.RequestService

class HomeUseCase(
    val getNotifications: GetNotifications,
    val getNews: GetNews,
    val getBook: GetBook
)

class GetNotifications(private val requestService: RequestService) {

    operator fun invoke() {

    }
}

class GetNews(private val requestService: RequestService) {

    operator fun invoke() {

    }
}

class GetBook(private val requestService: RequestService) {

    operator fun invoke() {

    }
}