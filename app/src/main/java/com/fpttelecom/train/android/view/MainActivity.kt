package com.fpttelecom.train.android.view

import android.content.res.Resources
import android.os.Bundle
import androidx.appcompat.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.base.BaseActivity
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.databinding.ActivityMainBinding
import com.fpttelecom.train.android.view.demo.Demo2Fragment
import com.fpttelecom.train.android.view.demo.DemoFragment
import com.fpttelecom.train.android.view.demoCallApi.GitFragment
import com.fpttelecom.train.android.view.demoDatabinding.DemoDataBindingFragment
import com.fpttelecom.train.android.view.mviDemo.MVIDemoFragment
import com.fpttelecom.train.android.view.sample_view.SampleFragment
import com.fpttelecom.train.android.view.tabChangeScroll.TabChangeScrollFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var ui: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater).apply {
            setContentView(root)
            makeNewScreenFlow(GitFragment())
        }

        val darkTheme: Resources.Theme =
            ContextThemeWrapper(applicationContext, R.style.Theme_Train).theme
//        window.navigationBarColor = resources.getColor(android.R.color.black, darkTheme)

        with(ui) {
            content.measureAllChildren
        }
    }

    fun <T : BaseFragment<*>> comeToScreen(fragment: T) {
        supportFragmentManager.commit {
            fragmentAlreadyExist(fragment)?.let { remove(it) }
            setReorderingAllowed(true)
            add(android.R.id.content, fragment, fragment::class.java.name)
            addToBackStack(fragment::class.java.name)
            attach(fragment)
        }
    }

    private fun <T : BaseFragment<*>> fragmentAlreadyExist(fragment: T): Fragment? {
        supportFragmentManager.fragments.forEach {
            if (it::class.java.name == fragment::class.java.name)
                return it
        }
        return null
    }
}