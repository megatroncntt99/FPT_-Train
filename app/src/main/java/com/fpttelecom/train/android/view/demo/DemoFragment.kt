package com.fpttelecom.train.android.view.demo

import android.annotation.SuppressLint
import androidx.appcompat.view.ContextThemeWrapper
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.base.BaseAdapter
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.data.model.DemoModel
import com.fpttelecom.train.android.databinding.FragmentDemoBinding
import com.fpttelecom.train.android.extensions.*
import com.fpttelecom.train.android.utils.LogCat
import com.fpttelecom.train.android.view.demo.adapter.DemoAdapter
import kotlinx.coroutines.delay
import java.util.*
import kotlin.collections.ArrayList


class DemoFragment : BaseFragment<FragmentDemoBinding>() {
    private val demoAdapter by lazy { DemoAdapter(requireContext()) }


    override fun getViewBinding() = FragmentDemoBinding.inflate(layoutInflater)

    override fun initViewModel() {

    }

    override fun initView() {
        initHeader()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        demoAdapter.itemListener(object : DemoAdapter.ItemListener {
            override fun onItemClick(model: DemoModel) {
                LogCat.d(model.placeName)
            }
        })


        getVB().rvDemo.adapter = demoAdapter
        val a = arrayListOf<DemoModel>()
        for (i in 0 until 20) {
            a.add(DemoModel("<PlaceName> $i", false))
        }
        demoAdapter.setData(a)


        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return if (viewHolder.itemViewType == BaseAdapter.VIEW_TYPE_HEADER || viewHolder.itemViewType==BaseAdapter.VIEW_TYPE_FOOTER)
                    makeMovementFlags(0, 0)
                else makeMovementFlags(ItemTouchHelper.UP or ItemTouchHelper.DOWN, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                if (viewHolder.itemViewType != target.itemViewType)
                    return false
                val positionDragged =
                    viewHolder.adapterPosition
                val positionTarget = target.adapterPosition

                LogCat.d(demoAdapter.getData()[positionDragged - 1].placeName + " <-----> " + demoAdapter.getData()[positionTarget - 1].placeName)
                Collections.swap(demoAdapter.getData(), positionDragged - 1, positionTarget - 1)
                demoAdapter.notifyItemMoved(positionDragged, positionTarget)
                demoAdapter.notifyItemChanged(positionDragged, false)
                demoAdapter.notifyItemChanged(positionTarget, false)
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

            }
        })
        itemTouchHelper.attachToRecyclerView(getVB().rvDemo)

    }

    private fun initHeader() {
        getVB().header.tvTitle.text = "Di chuyển camera"
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun clickView() {
        getVB().header.imageBack.onClick {
            moveBack()
        }
        getVB().btnConfirm.onClick {
            if ( getVB().tvConfirm.isVisible()){
                getVB().tvConfirm.gone()
                getVB().progressLoading.visible()
                getVB().btnConfirm.isEnabled=false
                launchWhenCreated {
                    delay(1000)
                    getVB().btnConfirm.isEnabled=true
                }
            }else {
                getVB().tvConfirm.visible()
                getVB().progressLoading.gone()
            }

        }

    }

    override fun flowOnce() {

    }

    override fun onComeBack() {

    }
}