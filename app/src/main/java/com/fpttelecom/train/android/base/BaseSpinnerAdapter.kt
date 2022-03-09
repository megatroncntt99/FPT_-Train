package com.fpttelecom.train.android.base

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.fpttelecom.train.android.R
import java.util.*

abstract class BaseSpinnerAdapter<T, VH : BaseSpinnerAdapter.BaseViewHolder, HH : BaseSpinnerAdapter.BaseHintHolder>(
    context: Context,
    isShowHint: Boolean
) :
    ArrayAdapter<T?>(context, 0) {
    private val data: MutableList<T> = ArrayList()
    private var isShowHint = true

    fun addData(list: List<T>?) {
        if (list != null && list.isNotEmpty()) {
            data.addAll(list)
            notifyDataSetChanged()
        }
    }

    fun setShowHint(showHint: Boolean) {
        isShowHint = showHint
    }

    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun getData(): List<T> {
        return data
    }

    fun setData(list: List<T>?) {
        if (list != null && list.isNotEmpty()) {
            data.clear()
            data.addAll(list)
            notifyDataSetChanged()
        }
    }

    override fun getCount(): Int {
        return if (isShowHint) data.size + 1 else data.size
    }

    override fun getItem(position: Int): T? {
        return data[if (isShowHint) position - 1 else position]
    }

    override fun getItemId(position: Int): Long {
        return if (isShowHint) (position - 1).toLong() else position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        convertView = if (isShowHint) {
            if (position == 0) {
                bindHintView(convertView, parent, provideHintResource())
            } else {
                bindItemView(convertView, parent, position - 1, provideItemViewResource())
            }
        } else {
            bindItemView(convertView, parent, position, provideItemViewResource())
        }
        return convertView
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        convertView = if (isShowHint) {
            if (position == 0) {
                createView(parent, R.layout.x_item_default_empty_vertical)
            } else {
                bindItemView(convertView, parent, position - 1, provideItemDropDownViewResource())
            }
        } else {
            bindItemView(convertView, parent, position, provideItemDropDownViewResource())
        }
        return convertView
    }

    private fun bindHintView(convertView: View?, parent: ViewGroup, layoutResource: Int): View {
        var convertView = convertView
        convertView = createView(parent, layoutResource)
        val hintHolder: HH = provideHintHolder()
        hintHolder.bindViews(convertView)
        return convertView
    }

    private fun bindItemView(
        convertView: View?,
        parent: ViewGroup,
        myPosition: Int,
        layoutResource: Int
    ): View {
        var convertView = convertView
        val item = data[myPosition]
        convertView = createView(parent, layoutResource)
        val viewHolder: VH = provideViewHolder()
        viewHolder.bindViews(convertView)
        viewHolder.bindData(myPosition)
        return convertView
    }

    protected fun provideHintResource(): Int {
        return R.layout.x_item_hint_spinner
    }

    protected abstract fun provideItemViewResource(): Int
    protected abstract fun provideItemDropDownViewResource(): Int
    protected abstract fun provideViewHolder(): VH
    protected abstract fun provideHintHolder(): HH
    protected fun createView(parent: ViewGroup, layoutResource: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)
    }

    abstract class BaseViewHolder {
        abstract fun bindViews(view: View?)
        abstract fun bindData(position: Int)
    }

    abstract class BaseHintHolder {
        abstract fun bindViews(view: View?)
    }

    init {
        this.isShowHint = isShowHint
    }
}