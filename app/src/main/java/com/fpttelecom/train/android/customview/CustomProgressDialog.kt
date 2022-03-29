package com.fpttelecom.train.android.customview

import android.app.ProgressDialog
import android.content.Context
import com.fpttelecom.train.android.R

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 4:28 PM
 */

class CustomProgressDialog(context: Context?, theme: Int) :
    ProgressDialog(context, theme) {
    override fun show() {
        try {
            super.show()
            setContentView(R.layout.dialog_fragment_loading)
        } catch (ignore: Exception) {
        }
    }
}