package com.fpttelecom.train.android.view.demo

import android.os.Handler
import android.os.Looper
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.base.BaseFragment
import com.fpttelecom.train.android.data.model.DemoModel
import com.fpttelecom.train.android.databinding.FragmentDemoBinding
import com.fpttelecom.train.android.extensions.onClick
import com.fpttelecom.train.android.utils.LogCat
import com.fpttelecom.train.android.view.demo.adapter.DemoAdapter
import java.util.*
import kotlin.collections.ArrayList


class DemoFragment : BaseFragment<FragmentDemoBinding>() {
    private  val demoAdapter= DemoAdapter { model ->
        LogCat.d(model.placeName)
    }


    override fun getViewBinding() = FragmentDemoBinding.inflate(layoutInflater)

    override fun initViewModel() {

    }

    override fun initView() {
        initHeader()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        demoAdapter

        getVB().rvDemo.adapter = demoAdapter
        for (i in 0 until 20) {
            demoAdapter.data.add(DemoModel("<PlaceName> $i", false))
        }
        demoAdapter.notifyDataSetChanged()

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            0
        ) {
            override fun getMovementFlags(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                return if (viewHolder.itemViewType == DemoAdapter.VIEW_TYPE_HEADER)
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

                LogCat.d(demoAdapter.data[positionDragged - 1].placeName + " <-----> " + demoAdapter.data[positionTarget - 1].placeName)
                Collections.swap(demoAdapter.data, positionDragged - 1, positionTarget - 1)
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

    override fun clickView() {
        getVB().header.imageBack.onClick {
            moveBack()
        }
    }

    override fun flowOnce() {

    }

    override fun onComeBack() {

    }
}