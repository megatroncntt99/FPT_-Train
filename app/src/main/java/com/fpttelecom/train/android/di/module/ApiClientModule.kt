package com.fpttelecom.train.android.di.module

import android.app.Application
import com.fpttelecom.train.android.api.RequestService
import com.fpttelecom.train.android.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.internal.managers.ApplicationComponentManager
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiClientModule {

    val HEADER_AUTHORIZATION = "Authorization"
    val HEADER_LANG = "Accept-Language"
    val HEADER_API_KEY = "x-api-key"
    val HEADER_UUID = "x-request-id"
    val HEADER_HEAD_TOKEN = "BEARER "
    val HEADER_CONTENT_TYPE = "Content-Type"
    val HEADER_CONTENT_TYPE_VALUE_JSON = "application/json"
    val CMC_API_KEY = "Nếu có"

    @Provides
    @Singleton
    fun provideHttpKtorClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            level = if (BuildConfig.DEBUG) LogLevel.ALL else LogLevel.NONE
        }
        install(JsonFeature) {
            serializer = GsonSerializer {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
        defaultRequest {
            host = "api.github.com"
            url { protocol = URLProtocol.HTTP }
        }
        engine {
            connectTimeout = 30_000
            threadsCount = 4
            pipelining = true
        }
    }

    @Provides
    @Singleton
    fun provideRequestService(client: HttpClient, activity: Application): RequestService {
        return RequestService(client, activity)
    }
}