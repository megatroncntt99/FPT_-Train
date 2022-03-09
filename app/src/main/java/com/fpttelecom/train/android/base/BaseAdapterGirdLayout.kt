package com.fpttelecom.train.android.base

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fpttelecom.train.android.R
import java.util.ArrayList

/**
 * Created by Nguyễn Văn Vân on 12/20/2021.
 */
abstract class BaseAdapterGirdLayout<IVH : BaseAdapterGirdLayout.BaseItemViewHolder, IL : BaseAdapterGirdLayout.BaseItemListener, DT> :
    RecyclerView.Adapter<RecyclerView.ViewHolder?>() {
    private var data: MutableList<DT> = ArrayList()
    protected var itemListener: IL? = null
    private var showLoadingMore = false
    private var showLoading = true
    private var isError = false
    private var mContext: Context? = null

    /*Xử lý dữ liệu*/
    @SuppressLint("NotifyDataSetChanged")
    @Synchronized
    fun addData(items: List<DT>?) {
        val oldRange = data.size
        if (items != null && items.isNotEmpty()) {
            data.addAll(items)
            notifyItemRangeChanged(oldRange, data.size)
        } else notifyDataSetChanged()
        loadingOff(false)
    }

    @SuppressLint("NotifyDataSetChanged")
    @Synchronized
    fun removeData(items: List<DT>?) {
        val oldRange = data.size
        if (items != null && items.isNotEmpty()) {
            for (i in items.indices) data.remove(items[i])
            notifyDataSetChanged()
        }
        loadingOff(false)
    }

    fun getData(): List<DT> {
        return data
    }

    @Synchronized
    fun setData(items: List<DT>?) {
        clearData()
        addData(items)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        data.clear()
        loadingOff(false)
        notifyDataSetChanged()
    }

    fun setShowLoadingMore(loadingMore: Boolean) {
        showLoadingMore = loadingMore
    }
    fun itemListener(itemListener: IL?) {
        this.itemListener = itemListener
    }

    /*Xử lý trạng thái*/
    @SuppressLint("NotifyDataSetChanged")
    fun loadingOn(currentPage: Int) {
        if (currentPage == 0 && !showLoading) {
            isError = false
            showLoading = true
            //notifyItemRangeChanged(0, 1);
            notifyDataSetChanged()
        } else if (!showLoading) {
            showLoadingMore = true
            if (isError) {
                isError = false
            } else {
                notifyDataSetChanged()
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
        notifyDataSetChanged()
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

    protected fun getLoadMoreErrorViewHolder(parent: ViewGroup): LoadMoreErrorViewHolder {
        return LoadMoreErrorViewHolder(createView(parent, R.layout.x_layout_list_load_more_error))
    }

    protected fun getErrorViewHolder(parent: ViewGroup): ErrorViewHolder {
        return ErrorViewHolder(createView(parent, R.layout.x_layout_list_error))
    }

    protected fun getLoadingViewHolder(parent: ViewGroup): LoadingViewHolder {
        return LoadingViewHolder(createView(parent, R.layout.x_layout_list_loading))
    }

    protected fun getLoadMoreViewHolder(parent: ViewGroup): LoadMoreViewHolder {
        return LoadMoreViewHolder(createView(parent, R.layout.x_item_loading_more_linear))
    }

    protected fun getEmptyViewHolder(parent: ViewGroup): EmptyViewHolder {
        return EmptyViewHolder(createView(parent, R.layout.x_layout_list_empty))
    }

    protected fun getHeaderViewHolder(parent: ViewGroup): HeaderViewHolder {
        return HeaderViewHolder(createView(parent, R.layout.x_item_default_empty_vertical))
    }

    protected fun getFooterViewHolder(parent: ViewGroup): FooterViewHolder {
        return FooterViewHolder(createView(parent, R.layout.x_item_default_empty_vertical))
    }

    protected abstract fun getCustomItemViewHolder(parent: ViewGroup): IVH
    protected fun createView(parent: ViewGroup, layoutResource: Int): View {
        return LayoutInflater.from(parent.context).inflate(layoutResource, parent, false)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is BaseAdapterGirdLayout<*, *, *>.HeaderViewHolder) {
            holder.bindData(position)
        } else if (holder is BaseAdapterGirdLayout<*, *, *>.FooterViewHolder) {
            holder.bindData(position)
        } else if (holder is BaseAdapterGirdLayout<*, *, *>.EmptyViewHolder) {
            holder.bindData(position)
        } else if (holder is BaseAdapterGirdLayout<*, *, *>.ErrorViewHolder) {
            holder.bindData(position)
        } else if (holder is BaseAdapterGirdLayout<*, *, *>.LoadMoreErrorViewHolder) {
            holder.bindData(position)
        } else if (holder is BaseItemViewHolder) (holder).bindData(position)
    }

    override fun getItemCount(): Int {
        return if (showLoading || isError) {
            if (isError && data.size > 0) {
                data.size
            } else 0
        } else data.size
    }

    override fun getItemViewType(position: Int): Int {
        if (showLoading) return VIEW_TYPE_LOADING
        if (isError) {
            if (data.size == 0) return VIEW_TYPE_LIST_ERROR else if (position == data.size + 1) return VIEW_TYPE_FOOTER
        }
        return if (data.size == 0) {
            VIEW_TYPE_LIST_EMPTY
        } else VIEW_TYPE_ITEM
    }


    fun setmContext(mContext: Context?) {
        this.mContext = mContext
    }

    fun getmContext(): Context? {
        return mContext
    }

    interface BaseItemListener {
        fun onRetryClick()
    }

    abstract class BaseItemViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var positionItem = -1
        protected var isBindData = true

        protected fun currentDataPosition(): Int {
            return adapterPosition - 1
        }

        protected abstract fun setupView()
         open fun bindData(position: Int) {
            this.positionItem = position
            isBindData = true
        }

        init {
            setupView()
        }
    }

    fun isPositionFooter(position: Int): Boolean {
        return position == itemCount - 1 && showLoadingMore
    }

    inner class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(
        itemView
    ) {
        protected fun setupView() {}
        fun bindData(position: Int) {}

        init {
            setupView()
        }
    }

    inner class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected fun setupView() {}
        fun bindData(position: Int) {}

        init {
            setupView()
        }
    }

    inner class LoadMoreErrorViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        protected fun setupView() {}
        fun bindData(position: Int) {}

        init {
            setupView()
        }
    }

    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class LoadMoreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    inner class EmptyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        protected fun setupView() {}
        fun bindData(position: Int) {}

        init {
            setupView()
        }
    }

    inner class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buttonRetry: View by lazy { itemView.findViewById(R.id.buttonRetry) }

        fun setupView() {
            buttonRetry.setOnClickListener { if (itemListener != null) itemListener?.onRetryClick() }
        }

        fun bindData(position: Int) {}

        init {
            setupView()
        }
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
    }
}