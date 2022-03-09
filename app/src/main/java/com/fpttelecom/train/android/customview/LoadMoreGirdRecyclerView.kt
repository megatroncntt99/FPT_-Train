package com.fpttelecom.train.android.customview

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.utils.LogCat
import java.lang.Exception

/**
 * Created by Nguyễn Văn Vân on 12/20/2021.
 */
class LoadMoreGirdRecyclerView : RecyclerView {
    private var currentPage = 0
    private var maxPage = 0
    private var isLoading = false
    private var isLoadMoreError = false
    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var gridLayoutManager: GridLayoutManager? = null
    private var totalItemCount = 0
    private var lastVisibleItem = 0
    private var canScroll = true
    fun getGirdLayoutManager(): GridLayoutManager? {
        return gridLayoutManager
    }

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

    fun setOnLoadMoreListener(onLoadMoreListener: OnLoadMoreListener?) {
        this.onLoadMoreListener = onLoadMoreListener
    }

    private fun setDefaultLayoutManager(context: Context) {
        gridLayoutManager = object : GridLayoutManager(context, 2, VERTICAL, false) {
            override fun canScrollVertically(): Boolean {
                return canScroll
            }
        }
        this.layoutManager = gridLayoutManager
    }

    fun isCanScroll(): Boolean {
        return canScroll
    }

    fun setCanScroll(canScroll: Boolean) {
        this.canScroll = canScroll
    }

    private fun handleLoadMoreListener() {
        addOnScrollListener(object : OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy < 0) isLoadMoreError = false
                if (onLoadMoreListener != null && dy >= 0) {
                    totalItemCount = gridLayoutManager?.itemCount ?: 0
                    lastVisibleItem = gridLayoutManager?.findLastVisibleItemPosition() ?: -1
                    if (totalItemCount > 0 && lastVisibleItem >= totalItemCount - 3) {
                        if (isLoading) return
                        if (currentPage >= maxPage) return
                        if (isLoadMoreError) return
                        loadingOn()
                        try {
                            if (needShowLoadingMore()) onLoadMoreListener?.onShowLoading()
                        } catch (e: Exception) {
                        }
                        Handler().postDelayed({
                            try {
                                if (onLoadMoreListener != null) onLoadMoreListener?.onLoadMore()
                            } catch (e: Exception) {
                                LogCat.e(e.message)
                            }
                        }, 200)
                    }
                }
            }
        })
    }

    private fun needShowLoadingMore(): Boolean {
        try {
            val height = gridLayoutManager?.height ?: 0
            var itemsHeight = 0
            val count = gridLayoutManager?.childCount ?: 0
            for (i in 0 until count) itemsHeight += gridLayoutManager?.getChildAt(i)?.height ?: 0
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