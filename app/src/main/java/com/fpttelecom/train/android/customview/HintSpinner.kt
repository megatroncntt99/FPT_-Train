package com.fpttelecom.train.android.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.AppCompatSpinner

/**
 * Created by Nguyễn Văn Vân on 12/9/2021.
 */
class HintSpinner : AppCompatSpinner {
    private var isFirstCreate = true
    private var itemSelectListener: ItemSelectListener? = null

    constructor(context: Context) : super(context) {
        handleItemSelect()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(
        context, attrs
    ) {
        handleItemSelect()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        handleItemSelect()
    }

    private fun handleItemSelect() {
        onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                if (isFirstCreate) {
                    isFirstCreate = false
                    return
                }
                if (itemSelectListener != null) itemSelectListener?.onItemSelect(l.toInt())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    override fun setSelection(position: Int) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position)
        if (sameSelected && onItemSelectedListener != null) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            onItemSelectedListener?.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }

    override fun setSelection(position: Int, animate: Boolean) {
        val sameSelected = position == selectedItemPosition
        super.setSelection(position, animate)
        if (sameSelected && onItemSelectedListener != null) {
            // Spinner does not call the OnItemSelectedListener if the same item is selected, so do it manually now
            onItemSelectedListener?.onItemSelected(this, selectedView, position, selectedItemId)
        }
    }

    fun setItemSelectListener(itemSelectListener: ItemSelectListener) {
        this.itemSelectListener = itemSelectListener
    }

    interface ItemSelectListener {
        fun onItemSelect(position: Int)
    }
}