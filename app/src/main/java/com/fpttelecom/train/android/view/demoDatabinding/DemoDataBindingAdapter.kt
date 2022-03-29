package com.fpttelecom.train.android.view.demoDatabinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.data.model.DemoModel
import com.fpttelecom.train.android.databinding.ItemDemoDataBindingBinding
import com.fpttelecom.train.android.databinding.XItemLoadingMoreLinearBinding


/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 28,March,2022
 * Time: 1:54 PM
 */

class DemoDataBindingAdapter(private val demoListener: DemoListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val TYPE_ITEM = 1
    private val TYPE_FOOTER = 2
    private var mWithFooter = false
    private val list = ArrayList<DemoModel>()
    fun unSelect() {
        list.forEachIndexed { index, model ->
            if (model.isSelect) {
                model.isSelect = false
                notifyItemChanged(index)
                return@forEachIndexed
            }
        }
    }

    fun updateListItem(newListItems: List<DemoModel>) {
        val diffCallback = object : DiffUtil.Callback() {

            override fun getOldListSize() = list.size;

            override fun getNewListSize() = newListItems.size


            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                list[oldItemPosition].placeName == newListItems[newItemPosition].placeName


            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                list[oldItemPosition] == newListItems[newItemPosition]

        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newListItems)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ItemViewHolder(private val binding: ItemDemoDataBindingBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setOnclick {
                binding.modelItem?.let {
                    onClickItemView(it)
                }
            }
        }

        fun setBinding(item: DemoModel, position: Int) {
            binding.apply {
                modelItem = item
                executePendingBindings()
            }
        }

        private fun onClickItemView(item: DemoModel) {
            if (!item.isSelect) unSelect()
            item.isSelect = !item.isSelect
            notifyItemChanged(adapterPosition)
            demoListener.onClickItemView(item)
        }

    }

    inner class LoadMoreViewHolder(binding: XItemLoadingMoreLinearBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM ->
                ItemViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.item_demo_data_binding,
                        parent,
                        false
                    )
                )
            else -> LoadMoreViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.x_item_loading_more_linear,
                    parent,
                    false
                )
            )
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            holder.setBinding(list[position], position)
    }

    override fun getItemCount(): Int {
        var itemCount: Int = list.size
        if (mWithFooter) itemCount++
        return itemCount
    }

    interface DemoListener {
        fun onClickItemView(item: DemoModel)
    }

    override fun getItemViewType(position: Int): Int {
        if (mWithFooter && isPositionFooter(position)) return TYPE_FOOTER
        return TYPE_ITEM
    }

    fun isPositionFooter(position: Int): Boolean {
        return position == itemCount - 1 && mWithFooter
    }

    fun setWithFooter(value: Boolean) {
        mWithFooter = value
        notifyItemChanged(itemCount)
    }
}