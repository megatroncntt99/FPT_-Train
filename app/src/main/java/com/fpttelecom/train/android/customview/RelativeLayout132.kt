package com.fpttelecom.train.android.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout
import kotlin.math.roundToInt

/**
 * Created by Nguyễn Văn Vân on 12/20/2021.
 */
class RelativeLayout132 :RelativeLayout{
    constructor(context: Context) : super(context) {}
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val newHeight = (width * 1.32F).roundToInt()
        super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(newHeight, MeasureSpec.EXACTLY))
    }
}