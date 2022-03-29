package com.fpttelecom.train.android.base

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.customview.CustomProgressDialog
import com.fpttelecom.train.android.utils.*
import org.greenrobot.eventbus.EventBus

abstract class BaseActivity : AppCompatActivity() {
    private var dialog: CustomProgressDialog? = null
    private val TIME_DELAYED_BACK_PRESS: Int = 250
    private var isFirstFragment = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onUserInteraction() {
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    fun progressBarView() {
        try {
            if (dialog == null) {
                dialog = createDialogLoading()
            }
            if (dialog?.isShowing == false) {
                dialog?.show()
                Handler(Looper.getMainLooper()).postDelayed({ dialog?.dismiss() }, 30000)
            }
        } catch (e: java.lang.Exception) {

        }
    }

    fun dismissProgressBar() {
        try {
            if (dialog != null) {
                dialog?.dismiss()
            }
        } catch (e: java.lang.Exception) {

        }
    }

    open fun createDialogLoading(): CustomProgressDialog {
        val dialog = CustomProgressDialog(this, R.style.NewLoadingTheme)
        dialog.setOnDismissListener { _ -> { } }
        dialog.setCancelable(false)
        dialog.setCanceledOnTouchOutside(false)
        return dialog
    }

    fun hideKeyBoard() {
        val view = this.currentFocus
        if (view is EditText) {
            view.clearFocus()
            val imm =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0)
        }
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        LogCat.d("onTrimMemory:$level")
        when (level) {
            TRIM_MEMORY_COMPLETE -> {
                LogCat.d("TRIM_MEMORY_COMPLETE=$level")
            }
            TRIM_MEMORY_UI_HIDDEN -> {
                LogCat.d("TRIM_MEMORY_UI_HIDDEN=$level")
            }
            TRIM_MEMORY_BACKGROUND -> {
                LogCat.d("TRIM_MEMORY_BACKGROUND=$level")
            }
            TRIM_MEMORY_MODERATE -> {
                LogCat.d("TRIM_MEMORY_MODERATE=$level")
            }
            TRIM_MEMORY_RUNNING_CRITICAL -> {
                LogCat.d("TRIM_MEMORY_RUNNING_CRITICAL=$level")
            }
            TRIM_MEMORY_RUNNING_LOW -> {
                LogCat.d("TRIM_MEMORY_RUNNING_LOW=$level")
            }
            TRIM_MEMORY_RUNNING_MODERATE -> {
                LogCat.d("TRIM_MEMORY_RUNNING_MODERATE=$level")
            }
            else -> {
                LogCat.d("TRIM_MEMORY_UNKNOWN=$level")
            }
        }
    }

    open fun lockBackPress() {
        lockBackPress = true
    }

    open fun unlockBackPress() {
        lockBackPress = false
    }


    //IOnBackPressed Handle backPress inside fragment
    override fun onBackPressed() {
        LogCat.d("Activity - onBackPressed")
        if (lockBackPress) return
        if (!canBackPress) return
        val fragment =
            supportFragmentManager.findFragmentById(FragmentUtils.CONTAINER_MAIN_FRAGMENT)
        if (fragment is IOnBackPressed && (fragment as IOnBackPressed?)?.onBackPressed() == true) {
            return
        }
        delayBackPress(TIME_DELAYED_BACK_PRESS)
        FragmentUtils.moveBack(supportFragmentManager, this)
    }

    /*SCREENS MOVING*/
    open fun <F : Fragment> makeNewScreenFlow(fragment: F) {
        goToScreen(
            FragmentUtils.CONTAINER_MAIN_ACTIVITY,
            fragment,
            FragmentUtils.FLAG_NEW_TASK,
            null
        )
    }

    open fun <F : Fragment> goToScreen(fragment: F) {
        val topFragment: Fragment = getTopFragment()
        if (BaseFragment.getName(fragment.javaClass)
                .equals(BaseFragment.getName(topFragment.javaClass))
        ) return
        goToScreen(FragmentUtils.CONTAINER_MAIN_FRAGMENT, fragment, FragmentUtils.FLAG_ADD, null)
    }

    open fun <F : BaseFragment<*>> goToNewFragmentFromNotification(
        fragment: F,
        openNewFragment: Boolean
    ) {
        if (openNewFragment) {
            goToScreen(
                FragmentUtils.CONTAINER_MAIN_FRAGMENT,
                fragment,
                FragmentUtils.FLAG_ADD,
                null
            )
        } else {
            val topFragment: BaseFragment<*>? = getTopFragment()
        }
    }

    open fun gotoNewBusDetailsFragment(fragment: Fragment) {
        if (supportFragmentManager.fragments.size > 0) {
            val frgTrans = supportFragmentManager.beginTransaction()
            for (i in supportFragmentManager.fragments.indices) {
                val mFragment = supportFragmentManager.fragments[i]
                if (mFragment != null && mFragment.javaClass.name == fragment.javaClass.name) {
                    frgTrans.remove(supportFragmentManager.fragments[i])
                }
            }
            frgTrans.commit()
        }
        goToScreen(FragmentUtils.CONTAINER_MAIN_FRAGMENT, fragment, FragmentUtils.FLAG_ADD, null)
    }

    open fun <F : Fragment> forceGotoScreen(fragment: F) {
        goToScreen(FragmentUtils.CONTAINER_MAIN_FRAGMENT, fragment, FragmentUtils.FLAG_ADD, null)
    }

    open fun <F : Fragment> goToScreen(
        containerLayoutResource: Int,
        fragment: F,
        actionFlag: Int,
        element: View?
    ) {
        BaseFragment.DELAYED_TIME_START_SCREEN = FragmentUtils.DELAYED_TIME_FOR_PREPARE_SCREEN
        delayBackPress(BaseFragment.DELAYED_TIME_START_SCREEN)
        hideKeyboard()
        when (actionFlag) {
            FragmentUtils.FLAG_ADD -> {
                FragmentUtils.add(
                    supportFragmentManager,
                    containerLayoutResource,
                    fragment,
                    true,
                    element,
                    true
                )
            }
            FragmentUtils.FLAG_REPLACE -> {
                FragmentUtils.replace(
                    supportFragmentManager,
                    containerLayoutResource,
                    fragment,
                    false,
                    element,
                    false
                )
            }
            FragmentUtils.FLAG_NEW_TASK -> {
                clearAllScreens()
                FragmentUtils.replace(
                    supportFragmentManager,
                    containerLayoutResource,
                    fragment,
                    true,
                    element,
                    false
                )
            }
        }
    }

    /*Hàm reload các fragment đã tồn tại trong vòng đời hoạt động của ứng dụng. Nhằm mục đích rõ ràng hơn trong*/
    open fun <F : Fragment> reloadFragment(
        containerLayoutResource: Int,
        fragment: F, element: View?
    ) {
        BaseFragment.DELAYED_TIME_START_SCREEN = FragmentUtils.DELAYED_TIME_FOR_PREPARE_SCREEN
        delayBackPress(BaseFragment.DELAYED_TIME_START_SCREEN)
        hideKeyboard()
        FragmentUtils.remove(supportFragmentManager, fragment.javaClass.name)
        FragmentUtils.add(
            supportFragmentManager,
            containerLayoutResource,
            fragment,
            true,
            element,
            true
        )
    }

    open fun <F : Fragment> reloadFragment(fragment: F) {
        LogCat.e("reloadFragment: ${fragment::class.java.name}")
        reloadFragment(FragmentUtils.CONTAINER_MAIN_FRAGMENT, fragment, null)
    }
    /*kết thúc hàm reload*/

    //TODO: Khi nào cần dùng thì hãy import
//    var currentDialogFragment: BaseDialogFragment? = null
//    var currentDialogBottom: BaseBottomSheetDialogFragment? = null
//
//    open fun <D : BaseDialogFragment?> showDialogFragment(dialogFragment: D) {
//        hideKeyboard()
//        Handler().postDelayed({
//
//            // To avoid Fatal Exception: java.lang.IllegalStateException
//            // Can not perform fragment commit action after onSaveInstanceState
//            if (isSavedInstanceStateDone()) return@postDelayed
//            if (currentDialogFragment != null && currentDialogFragment.isShowing()) return@postDelayed
//            FragmentUtils.showDialog(supportFragmentManager, dialogFragment)
//            currentDialogFragment = dialogFragment
//        }, 10)
//    }
//
//    open fun <D : BaseDialogFragment?> showDialogFragment(dialogFragment: D, isShowDupDialog: Boolean) {
//        hideKeyboard()
//
//        // To avoid Fatal Exception: java.lang.IllegalStateException
//        // Can not perform fragment commit action after onSaveInstanceState
//        if (isSavedInstanceStateDone()) return
//        if (!isShowDupDialog) {
//            if (currentDialogFragment != null && currentDialogFragment.isShowing()) return
//        }
//        FragmentUtils.showDialog(supportFragmentManager, dialogFragment)
//        currentDialogFragment = dialogFragment
//    }
//
//    open fun <D : BaseBottomSheetDialogFragment?> showBottomDialogFragment(bottomDialogFragment: D) {
//        hideKeyboard()
//
//        // To avoid Fatal Exception: java.lang.IllegalStateException
//        // Can not perform fragment commit action after onSaveInstanceState
//        if (isSavedInstanceStateDone()) return
//        bottomDialogFragment.show(supportFragmentManager, "")
//        currentDialogBottom = bottomDialogFragment
//    }
//
//    open fun dismissDialog() {
//        if (currentDialogFragment != null && currentDialogFragment.isShowing()) {
//            currentDialogFragment.dismiss()
//            currentDialogFragment = null
//        }
//        if (currentDialogBottom != null && currentDialogBottom.isShowing()) {
//            currentDialogBottom.dismiss()
//            currentDialogBottom = null
//        }
//    }

    open fun backToScreen(screenName: String) {
        hideKeyboard()
        FragmentUtils.backToFragment(supportFragmentManager, screenName)
    }

    open fun getFragment(fragmentTag: String?): BaseFragment<*> {
        hideKeyboard()
        return FragmentUtils.getFragment(supportFragmentManager, fragmentTag) as BaseFragment<*>
    }

    open fun moveBack() {
        hideKeyboard()
        FragmentUtils.moveBack(supportFragmentManager, this)
    }

    private fun clearAllScreens() {
        FragmentUtils.clearBackStack(supportFragmentManager)
    }

    open fun getTopFragment(): BaseFragment<*> {
        return supportFragmentManager.findFragmentByTag(getTopFragmentName()) as BaseFragment<*>
    }

    protected open fun getTopFragmentName(): String? {
        return supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1).name
    }

    private fun isFirstFragment(): Boolean {
        try {
            return supportFragmentManager.backStackEntryCount == 0
        } catch (e: Exception) {
            LogCat.e(e.message)
        }
        return false
    }

    open fun getFragmentCount(): Int {
        return supportFragmentManager.backStackEntryCount
    }

    private fun detectFirstFragment() {
        supportFragmentManager.addOnBackStackChangedListener {
            isFirstFragment = supportFragmentManager.backStackEntryCount == 1
        }
    }

    open fun delayBackPress(time: Int) {
        canBackPress = false
        Handler(Looper.getMainLooper()).postDelayed({ canBackPress = true }, time.toLong())
    }

    /*SCREEN CREATION*/

    open fun showKeyboard(editText: EditText) {
        KeyboardUtils.showKeyboard(editText, this)
    }

    open fun hideKeyboard() {
        KeyboardUtils.hideKeyboard(this)
    }

    protected open fun registerEventBus() {
        EventBus.getDefault().register(this)
    }

    protected open fun unregisterEventBus() {
        EventBus.getDefault().unregister(this)
    }

    open fun setStatusBarContentWhite() {
        ScreenUtils.setOverlayStatusBar(window, true)
    }

    open fun setStatusBarContentBlack() {
        ScreenUtils.setOverlayStatusBar(window, false)
    }

    companion object {
        var DELAYED_TIME_START_SCREEN = FragmentUtils.DELAYED_TIME_FOR_PREPARE_SCREEN
        private var lockBackPress = false
        var canBackPress = true
        fun delayBackPress(time: Int) {
            canBackPress = false
            Handler(Looper.getMainLooper()).postDelayed({ canBackPress = true }, time.toLong())
        }

        fun isLockBackPress(): Boolean {
            return lockBackPress
        }
    }
}