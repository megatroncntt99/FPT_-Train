package com.fpttelecom.train.android

import android.content.Context
import android.os.Build.VERSION_CODES.Q
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.fpttelecom.train.android.room.AppDatabase
import com.fpttelecom.train.android.room.UserDao
import com.fpttelecom.train.android.room.UserEntity
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.*

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */



class ExampleUnitTest {
    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private  var database: AppDatabase? =null
    private  var dao: UserDao ? =null


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
    @Test
    fun `Insert user in app database`()= runTest {
        database= Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).allowMainThreadQueries().build()
        dao = database?.userDao()
        val userEntity = UserEntity(idUser = 123, login = "Elon musk", avatar_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQB0hzYZR7kiO4IYPcYg6LXRrkvk_u-PMKBgQ&usqp=CAU")
        dao?.insertUser(userEntity)
        val allUserEntity = dao?.gelAllUsers()
        Truth.assertThat(allUserEntity).contains(userEntity)
    }
}

@RunWith(AndroidJUnit4ClassRunner::class)
class UnitTestWithContextDemoTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    fun test_getPackageName() {
        Truth.assertThat(context.packageName).contains("your_package_name")
    }
}