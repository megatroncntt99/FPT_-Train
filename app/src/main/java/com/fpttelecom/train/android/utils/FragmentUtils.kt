package com.fpttelecom.train.android.utils

import android.app.Activity
import android.os.Build
import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.common.base.Preconditions
import com.fpttelecom.train.android.R
import java.lang.Exception

object FragmentUtils {

    const val DELAYED_TIME_FOR_PREPARE_SCREEN = 160
    const val CONTAINER_MAIN_FRAGMENT = android.R.id.content
    const val CONTAINER_MAIN_ACTIVITY = android.R.id.content

    /**
     * Replace current fragment with new fragment and addData new fragment to back stack
     */
    const val FLAG_ADD = 0

    /**
     * Replace current fragment with new one, not addData to back stack
     */
    const val FLAG_REPLACE = 1

    /**
     * Clear all fragments in back stack and addData new fragment
     */
    const val FLAG_NEW_TASK = 2
    var sDisableFragmentAnimations = false

    private fun FragmentUtils() {}

    fun addNotification(
        fragmentManager: FragmentManager,
        fragment: Fragment
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.setCustomAnimations(
            R.animator.slide_left_enter,
            R.animator.slide_left_exit
        )
        fragmentTransaction.add(CONTAINER_MAIN_FRAGMENT, fragment, getName(fragment.javaClass))
        fragmentTransaction.addToBackStack(getName(fragment.javaClass))
        fragmentTransaction.commit()
    }

    fun add(
        fragmentManager: FragmentManager,
        containerResource: Int,
        fragment: Fragment, canBack: Boolean, element: View?, animate: Boolean
    ) {
        addOrReplace(fragmentManager, containerResource, fragment, element, canBack, false, animate)
    }

    fun replace(
        fragmentManager: FragmentManager,
        containerResource: Int,
        fragment: Fragment,
        canBack: Boolean,
        element: View?, animate: Boolean
    ) {
        addOrReplace(fragmentManager, containerResource, fragment, element, canBack, true, animate)
    }

    fun replaceChild(
        fragmentManager: FragmentManager,
        containerResource: Int,
        fragment: Fragment
    ) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        //  fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.replace(containerResource, fragment, getName(fragment.javaClass))
        fragmentTransaction.commit()
    }


    private fun addOrReplace(
        fragmentManager: FragmentManager,
        containerResource: Int,
        fragment: Fragment,
        element: View?,
        canBack: Boolean,
        isReplace: Boolean, animate: Boolean
    ) {
        Preconditions.checkNotNull(fragmentManager)
        Preconditions.checkNotNull(fragment)
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (animate) setTransitionFragment(element, fragmentTransaction)
        if (isReplace) fragmentTransaction.replace(
            containerResource,
            fragment,
            getName(fragment.javaClass)
        ) else fragmentTransaction.add(
            containerResource,
            fragment,
            getName(fragment.javaClass)
        )
        if (canBack) fragmentTransaction.addToBackStack(getName(fragment.javaClass))
        fragmentTransaction.commit()
    }

    private fun setTransitionFragment(element: View?, fragmentTransaction: FragmentTransaction) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && element != null) {
            try {
                fragmentTransaction.addSharedElement(
                    element,
                    ViewCompat.getTransitionName(element) ?: ""
                )
            } catch (e: Exception) {
                setAnimationForTransitionFragment(fragmentTransaction)
            }
        } else {
            setAnimationForTransitionFragment(fragmentTransaction)
        }
    }

    private fun setAnimationForTransitionFragment(fragmentTransaction: FragmentTransaction) {
        fragmentTransaction.setCustomAnimations(
            R.animator.slide_left_enter,
            R.animator.slide_left_exit,
            R.animator.slide_right_enter,
            R.animator.slide_right_exit
        )
        //   fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
    }

    /**
     * Clear all fragments in back stack
     *
     * @param fragmentManager .
     */
    fun clearBackStack(fragmentManager: FragmentManager) {
        Preconditions.checkNotNull(fragmentManager)
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    fun backToFragment(fragmentManager: FragmentManager, fragmentName: String) {
        Preconditions.checkNotNull(fragmentManager)
        Preconditions.checkNotNull(fragmentName)
        if (fragmentManager.backStackEntryCount > 1) fragmentManager.popBackStack(fragmentName, 0)
    }

    fun moveBack(fragmentManager: FragmentManager, activity: Activity) {
        Preconditions.checkNotNull(fragmentManager)
        Preconditions.checkNotNull(activity)
        val count = fragmentManager.backStackEntryCount
        if (count > 2) {
            LogCat.e("moveBack -> popBackStack")
            fragmentManager.popBackStack()
        } else {
            LogCat.e("moveBack -> runOutOfApp")
            activity.moveTaskToBack(true)
        }
        for (i in fragmentManager.fragments.indices) {
            val mFragment = fragmentManager.fragments[i]
            if (mFragment != null) LogCat.e("Con lai: " + mFragment.javaClass.name + " id: " + mFragment.id) else {
                LogCat.e("Fragment null")
            }
        }
    }

    fun getName(classType: Class<*>): String {
        Preconditions.checkNotNull(classType)
        return classType.canonicalName
    }

    fun remove(manager: FragmentManager, fragmentName: String) {
        LogCat.e("Call Remove Fragment by Tag: $fragmentName")
        if (manager.fragments.size > 0) {
            for (i in manager.fragments.indices) {
                val mFragment = manager.fragments[i]
                if (mFragment != null && mFragment.javaClass.name == fragmentName) {
                    LogCat.e("Remove fragment: " + mFragment.javaClass.name)
                    //isRemoved = true;
                    val frgTrans = manager.beginTransaction()
                    frgTrans.remove(manager.fragments[i])
                    frgTrans.commit()
                }
            }
        }
    }

    /* fun <D : BaseDialogFragment?> showDialog(manager: FragmentManager, dialogFragment: D) {
         if (!manager.isStateSaved) {
             dialogFragment.show(manager, getName(dialogFragment.getClass()))
         }
     }*/

    fun isFragmentInBackstack(fragmentManager: FragmentManager, classType: Class<*>): Boolean {
        Preconditions.checkNotNull(fragmentManager)
        Preconditions.checkNotNull(classType)
        val fragmentName = getName(classType)
        for (i in 0 until fragmentManager.backStackEntryCount) {
            if (fragmentName.equals(
                    fragmentManager.getBackStackEntryAt(i).name,
                    ignoreCase = true
                )
            ) {
                return true
            }
        }
        return false
    }

    fun isFragmentInBackstack(fragmentManager: FragmentManager, className: String): Boolean {
        Preconditions.checkNotNull(fragmentManager)
        for (i in 0 until fragmentManager.backStackEntryCount) {
            if (className.equals(fragmentManager.getBackStackEntryAt(i).name, ignoreCase = true)) {
                LogCat.e(
                    "isFragmentInBackstack -  input: " + className + " - find: " + fragmentManager.getBackStackEntryAt(
                        i
                    ).name
                )
                return true
            }
        }
        return false
    }

    fun getFragment(fragmentManager: FragmentManager, fragmentTag: String?): Fragment? {
        return fragmentManager.findFragmentByTag(fragmentTag)
    }
}