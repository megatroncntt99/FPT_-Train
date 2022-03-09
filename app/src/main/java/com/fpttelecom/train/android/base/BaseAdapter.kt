package com.fpttelecom.train.android.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Nguyễn Văn Vân on 12/9/2021.
 */
abstract class BaseAdapter<IVH : BaseAdapter.BaseItemViewHolder, IL : BaseAdapter.BaseItemListener, DT> :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val data: MutableList<DT> = ArrayList()
    protected var itemListener: IL? = null
    private var showLoadingMore = false
    private var showLoading = true
    private var isError = false

    /* Định dạng layout */
    private var isShowHeaderOnEmpty = false
    private var isShowFooterOnEmpty = false
    var context: Context? = null


    fun setShowHeaderOnEmpty(showHeaderOnEmpty: Boolean) {
        isShowHeaderOnEmpty = showHeaderOnEmpty
    }

    fun setShowFooterOnEmpty(showFooterOnEmpty: Boolean) {
        isShowFooterOnEmpty = showFooterOnEmpty
    }

    /*Xử lý dữ liệu*/
    @Synchronized
    fun addData(items: List<DT>?) {
        var startPosition = data.size
        var itemCount = data.size
        if (items != null && items.isNotEmpty()) {
            data.addAll(items)
            if (startPosition != 0) startPosition++
            itemCount += 2
            itemCount += items.size
        }
        loadingOff(false)
        if (itemCount != startPosition) notifyItemRangeChanged(startPosition, getItemCount()) else {
            if (getItemCount() > 0) notifyItemRangeChanged(
                getItemCount() - 1,
                getItemCount()
            ) else notifyDataSetChanged()
        }
    }

    /*Xử lý dữ liệu*/
    @Synchronized
    fun addDataGrid(items: List<DT>?) {
        val oldRange = data.size
        if (items != null && items.isNotEmpty()) {
            data.addAll(items)
            notifyItemRangeChanged(oldRange, data.size)
        } else notifyDataSetChanged()
        loadingOff(false)
    }


    @Synchronized
    fun setData(items: List<DT>?) {
        clearData()
        addData(items)
    }

    fun getData(): MutableList<DT> {
        return data
    }

    fun clearData() {
        val itemCount = itemCount
        data.clear()
        loadingOff(false)
        notifyDataSetChanged()
    }

    /*Xử lý trạng thái*/
    @SuppressLint("NotifyDataSetChanged")
    fun loadingOn(currentPage: Int) {
        if (currentPage == 0 && !showLoading) {
            isError = false
            showLoading = true
            notifyItemRangeChanged(0, 1)
        } else if (!showLoading) {
            showLoadingMore = true
            if (isError) {
                isError = false
                if (itemCount > 0) {
                    try {
                        notifyItemRemoved(itemCount - 1)
                        notifyItemRangeChanged(itemCount - 1, itemCount)
                    } catch (e: Exception) {
                        notifyDataSetChanged()
                    }
                } else notifyDataSetChanged()
            } else {
                if (itemCount > 0) {
                    notifyItemRangeInserted(itemCount, itemCount)
                } else notifyDataSetChanged()
            }
        }
    }

    fun loadingOff(isError: Boolean) {
        showLoadingMore = false
        showLoading = false
        if (isError) errorOn()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun errorOn() {
        isError = true
        if (itemCount > 0) notifyItemRangeChanged(
            itemCount - 1,
            itemCount
        ) else notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ITEM) getCustomItemViewHolder(parent) else if (viewType == VIEW_TYPE_LOADING_MORE) getLoadMoreViewHolder(
            parent
        ) else if (viewType == VIEW_TYPE_HEADER) getHeaderViewHolder(parent) else if (viewType == VIEW_TYPE_FOOTER) getFooterViewHolder(
            parent
        ) else if (viewType == VIEW_TYPE_LOADING) getLoadingViewHolder(parent) else if (viewType == VIEW_TYPE_LIST_ERROR) getErrorViewHolder(
            parent
        ) else if (viewType == VIEW_TYPE_LIST_LOAD_MORE_ERROR) getLoadMoreErrorViewHolder(parent) else if (viewType == VIEW_TYPE_LIST_EMPTY) getEmptyViewHolder(
            parent
        ) else getDefaultViewHolder(parent)
    }

    private fun getDefaultViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return FooterViewHolder(createView(parent, R.layout.x_item_default_empty_vertical))
    }

    open fun getLoadMoreErrorViewHolder(parent: ViewGroup): LoadMoreErrorViewHolder {
        return LoadMoreErrorViewHolder(createView(parent, R.layout.x_layout_list_load_more_error))
    }

    open fun getErrorViewHolder(parent: ViewGroup): ErrorViewHolder {
        return ErrorViewHolder(createView(parent, R.layout.x_layout_list_error))
    }

    open fun getLoadingViewHolder(parent: ViewGroup): LoadingViewHolder {
        return LoadingViewHolder(createView(parent, R.layout.x_layout_list_loading))
    }

    open fun getLoadMoreViewHolder(parent: ViewGroup): LoadMoreViewHolder {
        return LoadMoreViewHolder(createView(parent, R.layout.x_item_loading_more_linear))
    }

    open fun getEmptyViewHolder(parent: ViewGroup): EmptyViewHolder {
        return EmptyViewHolder(createView(parent, R.layout.x_layout_list_empty))
    }

    open fun getHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(createView(parent, R.layout.x_item_default_empty_vertical))
    }

    open fun getFooterViewHolder(parent: ViewGroup): FooterViewHolder {
        return FooterViewHolder(createView(parent, R.layout.x_item_default_empty_vertical))
    }

    protected abstract fun getCustomItemViewHolder(parent: ViewGroup): IVH

    protected fun createView(parent: ViewGroup, layoutResource: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is BaseAdapter<*, *, *>.HeaderViewHolder -> {
                holder.bindData(position)
            }
            is BaseAdapter<*, *, *>.FooterViewHolder -> {
                holder.bindData(position)
            }
            is BaseAdapter<*, *, *>.EmptyViewHolder -> {
                holder.bindData(position)
            }
            is BaseAdapter<*, *, *>.ErrorViewHolder -> {
                holder.bindData(position)
            }
            is BaseAdapter<*, *, *>.LoadMoreErrorViewHolder -> {
                holder.bindData(position)
            }
            is BaseItemViewHolder -> (holder).bindData(position - 1)
        }
        setFadeAnimation(holder.itemView)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = FADE_DURATION.toLong()
        view.startAnimation(anim)
    }

    override fun getItemCount(): Int {
        if (showLoading || isError) {
            return if (isError && data.size > 0) {
                data.size + 2
            } else 1
        }
        if (data.size == 0) {
            var countEmpty = 1
            if (isShowHeaderOnEmpty) countEmpty++
            if (isShowFooterOnEmpty) countEmpty++
            return countEmpty
        }
        return data.size + 2
    }

    override fun getItemViewType(position: Int): Int {
        if (showLoading) return VIEW_TYPE_LOADING
        if (isError) {
            if (data.size == 0) return VIEW_TYPE_LIST_ERROR else if (position == data.size + 1) return VIEW_TYPE_FOOTER
        }
        if (data.size == 0) {
            var countItem = 1
            if (isShowHeaderOnEmpty) {
                countItem++
                if (position == 0) return VIEW_TYPE_HEADER
            }
            return if (isShowFooterOnEmpty && position == countItem) VIEW_TYPE_FOOTER else VIEW_TYPE_LIST_EMPTY
        }
        if (position == 0) return VIEW_TYPE_HEADER
        return if (position == data.size + 1) {
            if (showLoadingMore) VIEW_TYPE_LOADING_MORE else VIEW_TYPE_FOOTER
        } else VIEW_TYPE_ITEM
    }


    fun itemListener(itemListener: IL?) {
        this.itemListener = itemListener
    }

    interface BaseItemListener {
        fun onRetryClick()
    }

    abstract class BaseItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var positionItem = -1

        protected fun currentDataPosition(): Int {
            return adapterPosition - 1
        }

        protected abstract fun setupView()
        open fun bindData(position: Int) {
            this.positionItem = position
        }

        init {
            setupView()
        }
    }

    open inner class HeaderViewHolder (itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        open fun setupView() {}
        fun bindData(position: Int) {}

        init {
            setupView()
        }
    }

    open inner class FooterViewHolder : RecyclerView.ViewHolder {
        constructor(itemView: View) : super(itemView) {
            setupView()
        }

        constructor(itemView: View, listener: FooterListener) : super(itemView) {
            setupView()
            itemView.setOnClickListener { listener.click() }
        }

        protected fun setupView() {}
        fun bindData(position: Int) {}
    }

    open inner class LoadMoreErrorViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        protected fun setupView() {}
        fun bindData(position: Int) {}

        init {
            setupView()
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    )

    inner class LoadMoreViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView)

    open inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        protected fun setupView() {}
        fun bindData(position: Int) {}

        init {
            setupView()
        }
    }

    open inner class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var buttonRetry: View = itemView.findViewById(R.id.buttonRetry)
        protected fun setupView() {
            buttonRetry.setOnClickListener { itemListener?.onRetryClick() }
        }

        fun bindData(position: Int) {}

        init {
            setupView()
        }
    }

    interface FooterListener {
        fun click()
    }

    companion object {
        protected const val VIEW_TYPE_ITEM = 0
        private const val VIEW_TYPE_LOADING_MORE = 1
        private const val VIEW_TYPE_LOADING = 2
        private const val VIEW_TYPE_LIST_ERROR = 3
        private const val VIEW_TYPE_LIST_EMPTY = 4
        private const val VIEW_TYPE_LIST_LOAD_MORE_ERROR = 5
        private const val VIEW_TYPE_HEADER = 6
        private const val VIEW_TYPE_FOOTER = 7
        private const val FADE_DURATION = 500
    }
}
