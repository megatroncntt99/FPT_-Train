package com.fpttelecom.train.android

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.fpttelecom.train.android.api.RequestService
import com.fpttelecom.train.android.di.usecase.GetListUser
import com.fpttelecom.train.android.di.usecase.GetListUserRoom
import com.fpttelecom.train.android.di.usecase.GitUseCase
import com.fpttelecom.train.android.di.usecase.InsertListUser
import com.fpttelecom.train.android.room.AppDatabase
import com.fpttelecom.train.android.room.UserRoomRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import javax.inject.Named
import javax.inject.Singleton

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/02/2023
 */

@Module
@InstallIn(SingletonComponent::class)
object TestAppModule {
    @Provides
    @Named("test_db")
    fun providerInMemoryDp(@ApplicationContext context: Context): AppDatabase =
        Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()

    @Provides
    @Singleton
    @Named("userRoomRepository")
    fun userRoomRepository(@Named("test_db") db: AppDatabase): UserRoomRepository {
        return UserRoomRepository(db.userDao())
    }
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
    @Named("test_http_client")
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
    @Named("test_request_service")
    fun provideRequestService(@Named("test_http_client") client: HttpClient,@ApplicationContext context: Context): RequestService {
        return RequestService(client, context)
    }


    @Provides
    @Named("test_git_use_case")
    fun  provideGitUseCase(
      @Named("test_request_service")  requestService: RequestService,
      @Named("userRoomRepository") userRoomRepository: UserRoomRepository
    ): GitUseCase {
        return GitUseCase(
            getListUser = GetListUser(requestService),
            insertListUser = InsertListUser(userRoomRepository),
            getListUserRoom = GetListUserRoom(userRoomRepository)
        )
    }
}