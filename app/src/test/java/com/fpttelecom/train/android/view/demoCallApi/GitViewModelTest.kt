package com.fpttelecom.train.android.view.demoCallApi

import com.fpttelecom.train.android.api.RequestState
import com.fpttelecom.train.android.api.UiState
import com.fpttelecom.train.android.di.usecase.GitUseCase
import io.mockk.mockk
import org.junit.Test
import org.junit.Assert.*

/**
 * Author: vannv8@fpt.com.vn
 * Date: 02/02/2023
 */
internal class GitViewModelTest {
    private val gitUseCase: GitUseCase = mockk()
    private val gitViewModel = GitViewModel(gitUseCase)

    @Test
    fun `When fetching user content state is shown`(){
        gitViewModel.getListUserGit()
        assert(gitViewModel.uiStateListUser.value.state == RequestState.SUCCESS)
    }
}