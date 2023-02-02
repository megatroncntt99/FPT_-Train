package com.fpttelecom.train.android.view.demoCallApi

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.data.model.UserResponse
import com.fpttelecom.train.android.databinding.FragmentGitBinding
import com.fpttelecom.train.android.extensions.*
import com.fpttelecom.train.android.utils.LogCat
import com.fpttelecom.train.android.utils.PermissionUtil
import com.fpttelecom.train.android.utils.PermissionUtil.launchMultiplePermission
import com.fpttelecom.train.android.utils.PermissionUtil.launchSinglePermission
import com.fpttelecom.train.android.utils.PermissionUtil.registerPermission
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
    private val viewmo1 by lazy {ViewModelProvider(this).get(GitViewModel::class.java)  }

    private val cameraPermission = registerPermission {
        onCameraPermissionResult(it)
    }

    private val storagePermission = registerPermission {
        onStoragePermissionResult(it)
    }

    //Get result with state
    private fun onStoragePermissionResult(state: PermissionUtil.PermissionState) {
        when (state) {
            PermissionUtil.PermissionState.Denied -> {
                LogCat.d("Denied Storage Permission")
            }
            PermissionUtil.PermissionState.Granted -> {
                LogCat.d("Granted Storage Permission")
            }
            PermissionUtil.PermissionState.PermanentlyDenied -> {
                LogCat.d("PermanentlyDenied Storage Permission")
            }
        }
    }

    //Get result with state
    private fun onCameraPermissionResult(state: PermissionUtil.PermissionState) {
        when (state) {
            PermissionUtil.PermissionState.Denied -> {
                LogCat.d("Denied Camera Permission")
            }
            PermissionUtil.PermissionState.Granted -> {
                LogCat.d("Granted Camera Permission")
            }
            PermissionUtil.PermissionState.PermanentlyDenied -> {
                LogCat.d("PermanentlyDenied Camera Permission")
            }
        }
    }


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
            DemoBottomSheetFragment(it).show(childFragmentManager, "");
        }
    }

    override fun clickView() {
        getVB().setOnClickCallApi {
            launchWhenCreated {
                requireContext().readDataStore("AAAAAA", String::class.java)?.let { LogCat.d(it) }
            }
            viewModel.getListUserGit()
        }
        getVB().setOnClickLoadLocal {
            viewModel.getListUserRoomDB()
        }

        getVB().setOnClickPermission {
            //Single
            cameraPermission.launchSinglePermission(android.Manifest.permission.CAMERA)

            // Multiple
            storagePermission.launchMultiplePermission(
                arrayOf(
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                )
            )
        }
    }

    override fun flowOnce() {
        getVB().rvList.delayOnLifecycle(2000L) {

        }
        requireView().delayOnLifecycle(100L) {
            launchWhenCreated {
                requireContext().saveDataStore("AAAAAA", "Van")
            }
        }

    }

    override fun onComeBack() {

    }
}