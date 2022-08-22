package com.fpttelecom.train.android.view.mviDemo

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.databinding.FragmentGitBinding
import com.fpttelecom.train.android.databinding.FragmentMviDemoBinding
import com.fpttelecom.train.android.extensions.launchWhenCreated
import com.fpttelecom.train.android.view.demoCallApi.GitViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

/**
 * Creator: Nguyen Van Van
 * Date: 28,April,2022
 * Time: 9:07 AM
 */

@AndroidEntryPoint
class MVIDemoFragment : BaseFragment<FragmentMviDemoBinding>() {
    private val viewModel by viewModels<MVIViewModel>()

    override fun getViewBinding() = FragmentMviDemoBinding.inflate(layoutInflater)

    override fun initViewModel() {
        GlobalScope.launch { 
            
        }
        launchWhenCreated {
            viewModel.userIntent.send(MainIntent.FetchUser)
        }
        runBlocking(){

        }
        lifecycleScope.launchWhenStarted {

        }

    }

    override fun initView() {

    }

    override fun clickView() {

    }

    override fun flowOnce() {

    }

    override fun onComeBack() {

    }
}