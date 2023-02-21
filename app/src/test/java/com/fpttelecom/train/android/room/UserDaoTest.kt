package com.fpttelecom.train.android.room

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Author: vannv8@fpt.com.vn
 * Date: 02/02/2023
 */

@OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
@RunWith(AndroidJUnit4::class)
@SmallTest
class UserDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var database: AppDatabase
    private lateinit var dao: UserDao

    @Before
    fun setup(){
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database.userDao()
    }
    @After
    fun teardown(){
        database.close()
    }

    @Test
    fun `Insert user in app database`()= runTest {
        val userEntity = UserEntity(idUser = 123, login = "Elon musk", avatar_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0hzYZR7kiO4IYPcYg6LXRrkvk_u-PMKBgQ&usqp=CAU")
        dao.insertUser(userEntity)
        val allUserEntity = dao.gelAllUsers()
        assertThat(allUserEntity).contains(userEntity)
    }
}