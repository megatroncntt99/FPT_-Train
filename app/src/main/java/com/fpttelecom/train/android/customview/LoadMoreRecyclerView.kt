package com.fpttelecom.train.android.customview

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.utils.LogCat

/**
 * Created by Nguyễn Văn Vân on 12/9/2021.
 */
class LoadMoreRecyclerView : RecyclerView {
    private var currentPage = 0
    private var maxPage = 0
    private var isLoading = false
    private var isLoadMoreError = false
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var linearLayoutManager: LinearLayoutManager =
        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    private var totalItemCount = 0
    private var lastVisibleItem = 0

    constructor(context: Context) : super(context) {
        setDefaultLayoutManager(context)
        handleLoadMoreListener()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setDefaultLayoutManager(context)
        handleLoadMoreListener()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        setDefaultLayoutManager(context)
        handleLoadMoreListener()
    }

    fun enableLoadMore() {
        updateLoadMore(1, 2)
    }

    fun disableLoadMore() {
        updateLoadMore(0, 0)
    }

    fun updateLoadMore(currentPage: Int, maxPage: Int) {
        if (currentPage != -1) {
            this.currentPage = currentPage
        }
        this.maxPage = maxPage
    }

    fun refreshLoadMore() {
        currentPage = 0
        maxPage = -1
    }

    fun nextPage(): Int {
        return currentPage + 1
    }

    fun currentPage(): Int {
        return currentPage
    }

    fun maxPage(): Int {
        return maxPage
    }

    fun loadingOn() {
        isLoading = true
    }

    fun loadingOff() {
        isLoading = false
    }

    fun errorOn() {
        isLoadMoreError = true
    }

    fun isLoading(): Boolean {
        return isLoading
    }

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    private fun setDefaultLayoutManager(context: Context) {
        this.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        linearLayoutManager = layoutManager as LinearLayoutManager
    }

    private fun handleLoadMoreListener() {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) isLoadMoreError = false
                if (onLoadMoreListener != null && dy >= 0) {
                    totalItemCount = linearLayoutManager.itemCount
                    linearLayoutManager.isAutoMeasureEnabled = true
                    lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                    if (totalItemCount > 0 && lastVisibleItem >= totalItemCount - 3) {
                        if (isLoading) return
                        if (currentPage >= maxPage) return
                        if (isLoadMoreError) return
                        loadingOn()
                        try {
                            if (needShowLoadingMore()) onLoadMoreListener?.onShowLoading()
                        } catch (e: Exception) {
                        }
                        Handler(Looper.getMainLooper()).postDelayed({
                            try {
                                if (onLoadMoreListener != null) onLoadMoreListener?.onLoadMore()
                            } catch (e: Exception) {
                                LogCat.e(e)
                            }
                        }, 200)
                    }
                }
            }
        })
    }

    private fun needShowLoadingMore(): Boolean {
        try {
            val height = linearLayoutManager.height
            var itemsHeight = 0
            val count = linearLayoutManager.childCount
            for (i in 0 until count) itemsHeight += linearLayoutManager.getChildAt(i)?.height ?: 0
            return itemsHeight >= height
        } catch (ignored: Exception) {
        }
        return true
    }

    interface OnLoadMoreListener {
        fun onShowLoading()
        fun onLoadMore()
    }
}