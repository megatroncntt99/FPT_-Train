package com.fpttelecom.train.android.view.demoCallApi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.data.model.DemoModel
import com.fpttelecom.train.android.data.model.UserResponse
import com.fpttelecom.train.android.databinding.ItemDemoDataBindingBinding
import com.fpttelecom.train.android.databinding.ItemUserGitBinding
import com.fpttelecom.train.android.view.demoDatabinding.DemoDataBindingAdapter

/**
 * Copyright by Intelin.
 * Creator: Nguyen Van Van
 * Date: 29,March,2022
 * Time: 3:43 PM
 */

class ListUserAdapter(val listener: (item: UserResponse) -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val list = ArrayList<UserResponse>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ItemViewHolder(
        DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_user_git,
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ItemViewHolder)
            holder.setBinding(list[position])
    }

    override fun getItemCount() = list.size

    fun updateListItem(newListItems: List<UserResponse>) {
        val diffCallback = object : DiffUtil.Callback() {

            override fun getOldListSize() = list.size;

            override fun getNewListSize() = newListItems.size


            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                list[oldItemPosition].id == newListItems[newItemPosition].id


            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
                list[oldItemPosition] == newListItems[newItemPosition]

        }
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        list.clear()
        list.addAll(newListItems)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ItemViewHolder(private val binding: ItemUserGitBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.setOnclick {
                binding.modelItem?.let {
                    onClickItemView(it)
                }
            }
        }

        fun setBinding(item: UserResponse) {
            binding.apply {
                modelItem = item

            }
        }

        private fun onClickItemView(item: UserResponse) {
            listener.invoke(item)
        }

    }
}