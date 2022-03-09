package com.fpttelecom.train.android.utils

import android.app.Activity
import android.content.Context
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import java.lang.Exception

object KeyboardUtils {

    fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboard(activity: Activity) {
        var view = activity.currentFocus
        if (view == null) view = View(activity)
        val imm = activity
            .getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun hideKeyboard(activity: Context?, view: View?) {
        if (view == null || activity == null) return
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun showKeyboard(edit: EditText, context: Context?) {
        if (context == null) return
        edit.isFocusable = true
        edit.isFocusableInTouchMode = true
        edit.requestFocus()
        try {
            edit.setSelection(edit.text.toString().trim { it <= ' ' }.length)
        } catch (ignored: Exception) {
        }
        val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm?.showSoftInput(edit, 0)
    }

    fun handleKeyboardTouchView(view: View?) {
        if (view == null) return
        view.setOnTouchListener(OnTouchListener { view, motionEvent ->
            if (motionEvent != null
                && (motionEvent.action == MotionEvent.ACTION_MOVE || motionEvent.action == MotionEvent.ACTION_UP)
            ) {
                val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                val isKeyboardUp = imm.isAcceptingText
                if (isKeyboardUp) {
                    imm.hideSoftInputFromWindow(view.windowToken, 0)
                }
            }
            false
        })
    }

}