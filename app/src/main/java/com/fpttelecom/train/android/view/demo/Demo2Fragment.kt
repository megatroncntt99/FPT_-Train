package com.fpttelecom.train.android.view.demo

import android.animation.ValueAnimator
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.databinding.FragmentDemo2Binding
import com.fpttelecom.train.android.extensions.getValueAnimator
import com.fpttelecom.train.android.extensions.onClick
import com.fpttelecom.train.android.utils.LogCat


/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 14,March,2022
 * Time: 12:59 PM
 */

class Demo2Fragment : BaseFragment<FragmentDemo2Binding>() {
    private var animator = ValueAnimator()

    override fun getViewBinding() = FragmentDemo2Binding.inflate(layoutInflater)

    override fun initViewModel() {

    }

    override fun initView() {
        initScrollView()
    }

    private fun initScrollView() {
        getVB().nestedScrollView.setOnScrollChangeListener { scrollView, _, scrollY, _, _ ->
            val view = (scrollView as ViewGroup).getChildAt(scrollView.childCount - 1)
            val diff: Int = view.bottom - (scrollView.getHeight() + scrollView.getScrollY())
            LogCat.d("Scroll View $diff -- $scrollY")
            when {
                scrollY == 0 -> {
                    checkFilter(getVB().rlNewest)
                }
//                diff == 0 -> {
//                    checkFilter(getVB().rlSearchManager)
//                }
            }
        }
    }

    private fun initScrollView1(linearLayout: LinearLayout) {
        val rect = Rect()
        val linearLayoutRect = Rect()
        val scrollViewRect = Rect()
        getVB().nestedScrollView.getHitRect(rect)
        linearLayout.getHitRect(linearLayoutRect)
        getVB().nestedScrollView.getDrawingRect(scrollViewRect)

        // Get coordinate relative to linear layout. See the note below.
        val correct_expected_bottom_y: Int = linearLayoutRect.top + rect.bottom
        val dy: Int = correct_expected_bottom_y - scrollViewRect.bottom
        if (dy > 0) {
            getVB().nestedScrollView.scrollBy(0, dy)
        }
    }

    override fun clickView() {
        getVB().rlNewest.onClick {
            getVB().nestedScrollView.scrollTo(0, 0)
        }
        getVB().rlBestSell.onClick {
            checkFilter(getVB().rlBestSell)
            initScrollView1(getVB().llHeaderVan2)
        }
        getVB().rlPrice.onClick {
            checkFilter(getVB().rlPrice)
            initScrollView1(getVB().llHeaderVan3)
        }
    }

    override fun flowOnce() {

    }

    override fun onComeBack() {

    }

    private fun setFilter(tv: TextView?, ll: RelativeLayout?) {
        getVB().tvNewest.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorPrimary
            )
        )
        getVB().tvBestSell.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorPrimary
            )
        )
        getVB().tvPrice.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorPrimary
            )
        )
        getVB().tvSearchManager.setTextColor(
            ContextCompat.getColor(
                requireActivity(),
                R.color.colorPrimary
            )
        )
        tv?.setTextColor(ContextCompat.getColor(requireActivity(), R.color.colorTextBlack))
        animationViewLine(ll)
    }

    private fun animationViewLine(ll: RelativeLayout?) {
        ll ?: return
        val lastX = getVB().vLine.translationX
        animator = getValueAnimator(
            true, 150, AccelerateDecelerateInterpolator()
        ) { progress -> getVB().vLine.translationX = (ll.x - lastX) * progress + lastX }
        animator.start()
    }

    private fun checkFilter(ll: RelativeLayout?) {
        ll ?: return

        when (ll.id) {
            getVB().rlNewest.id -> {
                setFilter(getVB().tvNewest, ll)
            }
            getVB().rlBestSell.id -> {
                setFilter(getVB().tvBestSell, ll)
            }
            getVB().rlPrice.id -> {
                setFilter(getVB().tvPrice, ll)
            }
            getVB().rlPrice.id -> {
                setFilter(getVB().tvPrice, ll)
            }
            getVB().rlSearchManager.id -> {
                setFilter(getVB().tvSearchManager, ll)
            }
            else -> {

            }
        }
    }
}