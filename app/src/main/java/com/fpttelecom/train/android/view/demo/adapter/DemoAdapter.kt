package com.fpttelecom.train.android.view.demo.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.base.BaseAdapter
import com.fpttelecom.train.android.data.model.DemoModel
import com.fpttelecom.train.android.extensions.gone
import com.fpttelecom.train.android.extensions.visible
import java.util.*
import kotlin.collections.ArrayList

class DemoAdapter(private val listener: (model: DemoModel) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    val data: ArrayList<DemoModel> = ArrayList()

    override fun getItemCount(): Int {
        return data.size + 1
    }

    fun unSelect() {
        data.forEach {
            it.isSelect = false
        }
        notifyDataSetChanged()
    }

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvPlaceName: TextView by lazy { view.findViewById<TextView>(R.id.tvPlaceName) }
        val ivCheck: ImageView by lazy { view.findViewById<ImageView>(R.id.ivCheck) }
        val line: View by lazy { view.findViewById<View>(R.id.line) }
    }

    inner class HeaderViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_demo, parent, false)
        ) else HeaderViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_demo_header, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> {
                if (data.size == position)
                    holder.line.gone()
                else holder.line.visible()
                val item = data[position - 1]
                holder.tvPlaceName.text = item.placeName
                holder.ivCheck.setImageResource(if (item.isSelect) R.drawable.ic_check else R.drawable.ic_unchecked)

                holder.itemView.setOnClickListener {
                    if (!item.isSelect) unSelect()
                    item.isSelect = !item.isSelect
                    notifyItemChanged(position)
                    listener.invoke(item)
                }
            }
            is HeaderViewHolder -> {

            }


        }
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return VIEW_TYPE_HEADER
        return VIEW_TYPE_ITEM
    }

    companion object {
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_HEADER = 6
    }
}


//    : BaseAdapter<DemoAdapter.ItemViewHolder, DemoAdapter.ItemListener, String>() {
//    private val selectionList = ArrayList<Boolean>()
//
//    override fun getCustomItemViewHolder(parent: ViewGroup) =
//        ItemViewHolder(createView(parent, R.layout.item_demo))
//
//    override fun getHeaderViewHolder(parent: ViewGroup) =
//        HeaderViewHolder(createView(parent, R.layout.item_demo_header))
//
//    interface ItemListener : BaseItemListener {
//        fun onItemClick(position: Int, selected: Boolean)
//        fun onItemLongClick(position: Int)
//    }
//
//    fun swap(positionForm: Int, positionTo: Int) {
//        Collections.swap(selectionList, positionForm, positionTo)
//    }
//
//
//    fun setSelection(size: Int) {
//        for (i in 0 until size)
//            selectionList.add(false)
//    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun unSelectAll() {
//        for (i in 0 until selectionList.size)
//            selectionList[i] = false
//        notifyDataSetChanged()
//    }
//
//
//    inner class ItemViewHolder(val view: View) : BaseAdapter.BaseItemViewHolder(view) {
//        private val tvPlaceName by lazy { view.findViewById<TextView>(R.id.tvPlaceName) }
//        private val ivCheck by lazy { view.findViewById<ImageView>(R.id.ivCheck) }
//        private val ivUnchecked by lazy { view.findViewById<ImageView>(R.id.ivUnchecked) }
//        private val line by lazy { view.findViewById<View>(R.id.line) }
//
//        override fun setupView() {
//
//        }
//
//        override fun bindData(position: Int) {
//            super.bindData(position)
//            initView(position)
//            clickView(position)
//        }
//
//
//        private fun initView(position: Int) {
//
//            ivCheck.setImageResource(if (selectionList[position]) R.drawable.ic_check else R.drawable.ic_unchecked)
//
//            tvPlaceName.text = getData()[position]
//        }
//
//        private fun clickView(position: Int) {
//            itemView.setOnClickListener { handleSelectedItems(position, !selectionList[position]) }
//        }
//
//        private fun handleSelectedItems(position: Int, isSelected: Boolean) {
//            selectionList[position] = isSelected
//            ivCheck.setImageResource(if (selectionList[position]) R.drawable.ic_check else R.drawable.ic_unchecked)
//            itemListener?.onItemClick(position, isSelected)
//        }
//
//    }
//
//}