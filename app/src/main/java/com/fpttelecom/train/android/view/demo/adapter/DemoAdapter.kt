package com.fpttelecom.train.android.view.demo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fpttelecom.train.android.R
import com.fpttelecom.train.android.base.BaseAdapter
import com.fpttelecom.train.android.data.model.DemoModel
import com.fpttelecom.train.android.databinding.ItemDemoBinding
import com.fpttelecom.train.android.databinding.ItemDemoHeaderBinding
import com.fpttelecom.train.android.extensions.gone
import com.fpttelecom.train.android.extensions.visible

class DemoAdapter(context: Context) : BaseAdapter<DemoAdapter.ItemViewHolder, DemoAdapter.ItemListener, DemoModel>(context) {

    fun unSelect() {
        getData().forEachIndexed { index, model ->
            if (model.isSelect) {
                model.isSelect = false
                notifyItemChanged(index + 1)
                return@forEachIndexed
            }

        }
    }

    override fun getCustomItemViewHolder(parent: ViewGroup) =
        ItemViewHolder(
            createView(
                ItemDemoBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        )

    override fun getHeaderViewHolder(parent: ViewGroup) =
        HeaderViewHolder(
            createView(
                ItemDemoHeaderBinding.inflate(
                    layoutInflater
                )
            )
        )

    interface ItemListener : BaseItemListener {
        fun onItemClick(model: DemoModel)
    }


    inner class ItemViewHolder(val view: View) : BaseAdapter.BaseItemViewHolder(view) {
        private val tvPlaceName by lazy { view.findViewById<TextView>(R.id.tvPlaceName) }
        private val ivCheck by lazy { view.findViewById<ImageView>(R.id.ivCheck) }
        private val line by lazy { view.findViewById<View>(R.id.line) }

        override fun setupView() {

        }

        override fun bindData(position: Int) {
            super.bindData(position)
            initView(position)
            clickView(position)
        }


        private fun initView(position: Int) {
            if (getData().size == position + 1)
                line.gone()
            else line.visible()
            ivCheck.setImageResource(if (getData()[position].isSelect) R.drawable.ic_check else R.drawable.ic_unchecked)

            tvPlaceName.text = getData()[position].placeName
        }

        private fun clickView(position: Int) {
            val item = getData()[position]
            itemView.setOnClickListener {
                if (!item.isSelect) unSelect()
                item.isSelect = !item.isSelect
                notifyItemChanged(position + 1)
                itemListener?.onItemClick(item)
            }
        }

    }

}