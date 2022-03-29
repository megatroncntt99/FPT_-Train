package com.fpttelecom.train.android.view.demoCallApi

import android.widget.Toast
import androidx.fragment.app.viewModels
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.data.model.UserResponse
import com.fpttelecom.train.android.databinding.FragmentGitBinding
import com.fpttelecom.train.android.extensions.handleStateFlow
import com.fpttelecom.train.android.extensions.launchWhenCreated
import com.fpttelecom.train.android.utils.LogCat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 3:27 PM
 */

@AndroidEntryPoint
class GitFragment : BaseFragment<FragmentGitBinding>() {

    private val viewModel by viewModels<GitViewModel>()

    override fun getViewBinding() = FragmentGitBinding.inflate(layoutInflater)

    override fun initViewModel() {
        launchWhenCreated {
            viewModel.uiStateListUser.collect {
                handleStateFlow(it, {
                    it.result?.let { it1 -> getListUserSuccess(it1) }
                }, { getListUserFail(it.message) })
            }
        }
        launchWhenCreated {
            viewModel.isLoading.collect {
                if (it)
                    showProgressBar()
                else hideProgressBar()
            }
        }
        launchWhenCreated {
            viewModel.uiMessage.collect {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getListUserSuccess(list: List<UserResponse>) {
        getVB().adapter?.updateListItem(list)

    }

    private fun getListUserFail(message: String?) {
        LogCat.d("FAIL $message")
    }

    override fun initView() {
        getVB().adapter = ListUserAdapter {
            LogCat.d(it.login)
        }
    }

    override fun clickView() {
        getVB().setOnClickCallApi {
            viewModel.getListUserGit()
        }
        getVB().setOnClickLoadLocal {
            viewModel.getListUserRoomDB()
        }
    }

    override fun flowOnce() {

    }

    override fun onComeBack() {

    }
}