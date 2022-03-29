package com.fpttelecom.train.android.view.demoDatabinding

import android.os.Handler
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.customview.LoadMoreRecyclerView
import com.fpttelecom.train.android.data.model.DemoModel
import com.fpttelecom.train.android.databinding.FragmentDemoDataBindingBinding
import com.fpttelecom.train.android.extensions.launchWhenCreated
import com.fpttelecom.train.android.utils.LogCat
import kotlinx.coroutines.delay

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 28,March,2022
 * Time: 11:54 AM
 */

class DemoDataBindingFragment : BaseFragment<FragmentDemoDataBindingBinding>(),
    DemoDataBindingAdapter.DemoListener {

    override fun getViewBinding() = FragmentDemoDataBindingBinding.inflate(layoutInflater)

    override fun initViewModel() {

    }

    override fun initView() {
        getVB().demoDataBindingAdapter = DemoDataBindingAdapter(this)
        val a = arrayListOf<DemoModel>()
        for (i in 0 until 20) {
            a.add(DemoModel("<PlaceName> $i", false))
        }
        getVB().demoDataBindingAdapter?.updateListItem(a)
        getVB().recyclerDemo.updateLoadMore(1, 5)
        getVB().recyclerDemo.setOnLoadMoreListener(object :
            LoadMoreRecyclerView.OnLoadMoreListener {
            override fun onShowLoading() {
                getVB().demoDataBindingAdapter?.setWithFooter(true)
            }

            override fun onLoadMore() {
                launchWhenCreated {
                    delay(3000)
//                    getVB().demoDataBindingAdapter?.setWithFooter(false)
//                    getVB().recyclerDemo.disableLoadMore()
                    for (i in 20 until 30) {
                        a.add(DemoModel("<PlaceName> $i", false))
                    }
                    getVB().demoDataBindingAdapter?.updateListItem(a)
                    getVB().recyclerDemo.loadingOff()
                    getVB().recyclerDemo.nextPage()
                    if(getVB().recyclerDemo.currentPage()==getVB().recyclerDemo.maxPage()){
                        getVB().demoDataBindingAdapter?.setWithFooter(false)
                    }
                }
            }

        })
    }

    override fun clickView() {

    }

    override fun flowOnce() {

    }

    override fun onComeBack() {

    }

    override fun onClickItemView(item: DemoModel) {
        LogCat.d(item.placeName)
    }
}