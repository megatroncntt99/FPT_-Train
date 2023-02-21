package com.fpttelecom.train.android


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.fpttelecom.train.android.api.RequestState
import com.fpttelecom.train.android.di.usecase.GitUseCase
import com.fpttelecom.train.android.extensions.handleStateFlow
import com.fpttelecom.train.android.room.AppDatabase
import com.fpttelecom.train.android.room.UserDao
import com.fpttelecom.train.android.room.UserEntity
import com.fpttelecom.train.android.view.demoCallApi.GitViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Named

/**
 * Author: vannv8@fpt.com.vn
 * Date: 03/02/2023
 */
@RunWith(RobolectricTestRunner::class)
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@OptIn(ExperimentalCoroutinesApi::class)
class UnitTestDemo {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: UserDao

    @Inject
    @Named("test_git_use_case")
    lateinit var gitUseCase: GitUseCase
    private lateinit var gitViewModel: GitViewModel

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.userDao()
        gitViewModel = GitViewModel(gitUseCase)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `Insert user in app database`() = runTest {
        val userEntity = UserEntity(
            id = 2,
            idUser = 123,
            login = "Elon musk",
            avatar_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0hzYZR7kiO4IYPcYg6LXRrkvk_u-PMKBgQ&usqp=CAU"
        )
        dao.insertUser(userEntity)
        val allUserEntity = dao.gelAllUsers()
        assertThat(allUserEntity).contains(userEntity)
    }

    @Test
    fun `delete user in app database`() = runTest {
        val userEntity = UserEntity(
            id = 2,
            idUser = 123,
            login = "Elon musk",
            avatar_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0hzYZR7kiO4IYPcYg6LXRrkvk_u-PMKBgQ&usqp=CAU"
        )
        dao.insertUser(userEntity)
        dao.deleteUser(userEntity)
        val allUserEntity = dao.gelAllUsers()
        assertThat(allUserEntity).doesNotContain(userEntity)
    }

    @Test
    fun `insert user 3 in app database`() = runTest {
        val userEntity1 = UserEntity(
            id = 1,
            idUser = 123,
            login = "Elon musk",
            avatar_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0hzYZR7kiO4IYPcYg6LXRrkvk_u-PMKBgQ&usqp=CAU"
        )
        val userEntity2 = UserEntity(
            id = 2,
            idUser = 123,
            login = "Elon musk",
            avatar_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0hzYZR7kiO4IYPcYg6LXRrkvk_u-PMKBgQ&usqp=CAU"
        )
        val userEntity3 = UserEntity(
            id = 3,
            idUser = 123,
            login = "Elon musk",
            avatar_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0hzYZR7kiO4IYPcYg6LXRrkvk_u-PMKBgQ&usqp=CAU"
        )
        dao.insertUser(userEntity1)
        dao.insertUser(userEntity2)
        dao.insertUser(userEntity3)
        val allUserEntity = dao.gelAllUsers()
        assertThat(allUserEntity).hasSize(3)
    }

    @Test
    fun `select all user git to `() = runTest {
        gitViewModel.getListUserGit()
    }
}