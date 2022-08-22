package com.fpttelecom.train.android.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.utils.LogCat
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 7:53 PM
 */

abstract class BaseBottomSheetDialogFragment<VB : ViewBinding> : BottomSheetDialogFragment() {
    private val DELAYED_TIME_FOR_INIT_SCREEN = 160L

    @Volatile
    protected var binding: VB? = null
    private val handler = Handler(Looper.getMainLooper())
    private val runnable = { flowOnce() }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogCat.d("Lifecycle in BottomSheetDialogFragment onCreate " + this.javaClass.name)
        init()
    }

     fun getBehavior(): BottomSheetBehavior<*>? {
        val dialog = dialog as BottomSheetDialog
        val bottomSheet: FrameLayout =
            dialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)
                ?: return null
        return BottomSheetBehavior.from(bottomSheet)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogCat.d("Lifecycle in BottomSheetDialogFragment onCreateView " + this.javaClass.name)
        return binding?.root?.apply {
            try {
                initView()
                clickView()
            } catch (e: Exception) {
                LogCat.e(e.message.toString())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogCat.d("Lifecycle in BottomSheetDialogFragment onActivityCreated " + this.javaClass.name)
        handler.postDelayed(runnable, DELAYED_TIME_FOR_INIT_SCREEN)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogCat.d("Lifecycle in BottomSheetDialogFragment onViewCreated " + this.javaClass.name)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogCat.d("Lifecycle in BottomSheetDialogFragment onDestroy " + this.javaClass.name)
    }

    override fun onStart() {
        super.onStart()
        LogCat.d("Lifecycle in BottomSheetDialogFragment onStart " + this.javaClass.name)
    }

    override fun onResume() {
        super.onResume()
        LogCat.d("Lifecycle in BottomSheetDialogFragment onResume " + this.javaClass.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        handler.removeCallbacks { runnable }
        LogCat.d("Lifecycle in BottomSheetDialogFragment onDestroyView " + this.javaClass.name)
    }

    override fun onDetach() {
        super.onDetach()
        LogCat.d("Lifecycle in BottomSheetDialogFragment onDetach " + this.javaClass.name)
    }

    private fun init() {
        binding = getViewBinding()
    }

    protected abstract fun getViewBinding(): VB

    /*Handle viewModel*/
    abstract fun initView()
    abstract fun clickView()
    abstract fun flowOnce()

    fun getVB(): VB = binding ?: synchronized(this) {
        binding = getViewBinding()
        getViewBinding()
    }

    fun showProgressBar() {
        (requireActivity() as BaseActivity).progressBarView()
    }

    fun hideProgressBar() {
        (requireActivity() as BaseActivity).dismissProgressBar()
    }
    fun hideKeyBoard() {
        (requireActivity() as BaseActivity).hideKeyBoard()
    }
}