package com.fpttelecom.train.android.view.tabChangeScroll

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.databinding.ItemSubCatBinding

/**
 * Creator: Nguyen Van Van
 * Date: 21,April,2022
 * Time: 4:19 PM
 */


class SubCategoryAdapter(var dataList: ArrayList<SubCategoryModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun getItemCount(): Int {
        return dataList.size
    }

    fun updateList(position: Int) {
        for ((index, model) in dataList.withIndex()) {
            model.isSelected = position == index
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SubCategoryViewHolder(
            ItemSubCatBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is SubCategoryViewHolder) {
            holder.bind()
        }
    }

    inner class SubCategoryViewHolder(val binding: ItemSubCatBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            dataList[layoutPosition].let { model ->
                binding.tvSubCatTextISF.text = model.name
                if (model.isSelected) {
                    binding.tvSubCatTextISF.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorBgWhite
                        )
                    )
                    binding.tvSubCatTextISF.background =
                        ContextCompat.getDrawable( binding.root.context, R.drawable.unslected_button)
                } else {
                    binding.tvSubCatTextISF.setTextColor(
                        ContextCompat.getColor(
                            binding.root.context,
                            R.color.colorTextBlack
                        )
                    )
                    binding.tvSubCatTextISF.background =
                        ContextCompat.getDrawable( binding.root.context, R.drawable.selected_button)
                }
            }
        }
    }

}