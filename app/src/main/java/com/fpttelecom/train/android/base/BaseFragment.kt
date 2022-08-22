package com.fpttelecom.train.android.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.fpttelecom.train.android.extensions.launchWhenCreated
import com.fpttelecom.train.android.utils.FragmentUtils
import com.fpttelecom.train.android.utils.LanguageUtils
import com.fpttelecom.train.android.utils.LogCat


abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private val DELAYED_TIME_FOR_INIT_SCREEN = 160L

    @Volatile
    protected var binding: VB? = null

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = { flowOnce() }

    open var useSharedViewModel: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogCat.d("Lifecycle in Fragment onCreate " + this.javaClass.name)
        initViewModel()
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        LogCat.d("Lifecycle in Fragment onCreateView " + this.javaClass.name)
        init()
        return binding?.root?.apply {
            try {
                setOnTouchListener { _, _ ->
                    hideKeyBoard()
                    return@setOnTouchListener false
                }
                initView()
                clickView()
            } catch (e: Exception) {
                onShowError(e.message.toString())
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        LogCat.d("Lifecycle in Fragment onActivityCreated " + this.javaClass.name)
        handler.postDelayed(runnable, DELAYED_TIME_FOR_INIT_SCREEN)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        LogCat.d("Lifecycle in Fragment onViewCreated " + this.javaClass.name)
        super.onViewCreated(view, savedInstanceState)
    }

    private fun init() {
        binding = getViewBinding()
    }

    private fun handleError() {
        launchWhenCreated {

        }
    }

    protected abstract fun getViewBinding(): VB

    /*Handle viewModel*/
    abstract fun initViewModel()
    abstract fun initView()
    abstract fun clickView()
    abstract fun flowOnce()
    abstract fun onComeBack()

    fun getVB(): VB = binding ?: synchronized(this) {
        binding = getViewBinding()
        getViewBinding()
    }

    fun progressBar(boolean: Boolean) {
        LogCat.i("progressBar $boolean")
        if (boolean) showProgressBar()
        else hideProgressBar()
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

    fun onShowError(mess: String) {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        LogCat.d("Lifecycle in Fragment onAttach " + this.javaClass.name)
    }

    override fun onPause() {
        super.onPause()
        LogCat.d("Lifecycle in Fragment onPause " + this.javaClass.name)
    }

    override fun onStop() {
        super.onStop()
        LogCat.d("Lifecycle in Fragment onStop " + this.javaClass.name)
    }

    override fun onDestroy() {
        super.onDestroy()
        LogCat.d("Lifecycle in Fragment onDestroy " + this.javaClass.name)
    }

    override fun onStart() {
        super.onStart()
        LogCat.d("Lifecycle in Fragment onStart " + this.javaClass.name)
    }

    override fun onResume() {
        super.onResume()
        LogCat.d("Lifecycle in Fragment onResume " + this.javaClass.name)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
        handler.removeCallbacks { runnable }
        LogCat.d("Lifecycle in Fragment onDestroyView " + this.javaClass.name)
    }

    override fun onDetach() {
        super.onDetach()
        LogCat.d("Lifecycle in Fragment onDetach " + this.javaClass.name)
    }

    companion object {
        var DELAYED_TIME_START_SCREEN = FragmentUtils.DELAYED_TIME_FOR_PREPARE_SCREEN
        fun getName(className: Class<*>): String {
            return FragmentUtils.getName(className)
        }
    }

    protected open fun setStatusBarColor() {
        setStatusBarContentBlack()
    }

    protected open fun setStatusBarContentWhite() {
        if (activity != null) (activity as BaseActivity).setStatusBarContentWhite()
    }

    protected open fun setStatusBarContentBlack() {
        if (activity != null) (activity as BaseActivity).setStatusBarContentBlack()
    }

    /*SCREEN CREATION*/
    open fun moveBack() {
        LogCat.e("moveBack")
        if (BaseActivity.isLockBackPress()) return
        if (!BaseActivity.canBackPress) return
        if (onBackPressed()) return
        BaseActivity.delayBackPress(DELAYED_TIME_START_SCREEN)
        if (activity != null) (activity as BaseActivity).moveBack()
    }

    protected open fun onBackPressed(): Boolean {
        return false
    }

    open fun <F : Fragment> goToScreen(fragment: F) {
        goToScreen(FragmentUtils.CONTAINER_MAIN_FRAGMENT, fragment, FragmentUtils.FLAG_ADD, null)
    }

    open fun <F : Fragment> goToScreen(
        containerLayoutResource: Int,
        fragment: F,
        actionFlag: Int, element: View?
    ) {
        DELAYED_TIME_START_SCREEN = FragmentUtils.DELAYED_TIME_FOR_PREPARE_SCREEN
        if (activity != null) (activity as BaseActivity).goToScreen(
            containerLayoutResource,
            fragment,
            actionFlag,
            element
        )
    }

    open fun <F : Fragment> reloadFragment(fragment: F) {
        if (activity != null) (activity as BaseActivity).reloadFragment(fragment)
    }

    open fun backToScreen(screenName: String) {
        if (activity != null) (activity as BaseActivity).backToScreen(screenName)
    }

    open fun makeCallToPhoneNumber(context: Context?, content: String?) {
        if (context == null || content == null) return
        val number = Uri.parse("tel:$content")
        val callIntent = Intent(Intent.ACTION_DIAL, number)
        callIntent.action = Intent.ACTION_DIAL
        if (callIntent.resolveActivity(context.packageManager) != null) {
            context.startActivity(callIntent)
        }
    }

    open fun openWebBrowser(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

}