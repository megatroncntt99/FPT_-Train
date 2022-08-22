package com.fpttelecom.train.android.view.tabChangeScroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.databinding.ItemCatBinding

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 4:13 PM
 */

class CategoryAdapter(var dataList: ArrayList<CatModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return CategoryViewHolder(
            ItemCatBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CategoryViewHolder) {
            holder.bind()
        }
    }

    inner class CategoryViewHolder(val binding: ItemCatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            dataList[layoutPosition]?.let { model ->
                binding.tvCatTextISF.text = model.name
            }
        }
    }

}