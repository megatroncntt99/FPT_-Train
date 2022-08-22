package com.fpttelecom.train.android.view.demoCallApi

import com.fpttelecom.train.android.base.BaseBottomSheetDialogFragment
import com.fpttelecom.train.android.data.model.UserResponse
import com.fpttelecom.train.android.databinding.BottomSheetFragmentDemoBinding

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 8:08 PM
 */

class DemoBottomSheetFragment(private val userResponse: UserResponse) :
    BaseBottomSheetDialogFragment<BottomSheetFragmentDemoBinding>() {
    override fun getViewBinding() = BottomSheetFragmentDemoBinding.inflate(layoutInflater)

    override fun initView() {
//        getBehavior()?.isDraggable = false
        isCancelable = false
        getVB().user = userResponse
    }

    override fun clickView() {
        isCancelable = true
    }

    override fun flowOnce() {

    }
}