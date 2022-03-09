package com.fpttelecom.train.android.utils

import android.graphics.Color
import android.view.View
import android.view.Window

/**
 * Created by Nguyễn Văn Vân on 12/9/2021.
 */
object ScreenUtils {
    fun setOverlayStatusBar(w: Window, statusBarColorLight: Boolean) {
        w.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        w.statusBarColor = Color.TRANSPARENT
        val lFlags = w.decorView.systemUiVisibility
        w.decorView.systemUiVisibility =
            if (statusBarColorLight) lFlags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() else lFlags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}