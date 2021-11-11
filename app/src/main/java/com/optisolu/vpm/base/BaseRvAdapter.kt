package com.optisolu.vpm.base

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRvAdapter<C, V: ViewDataBinding> : RecyclerView.Adapter<BaseViewHolder<V>>(){

    companion object {
        private const val TAG = "BaseRvAdapter"
    }

    protected val items = ArrayList<C>()

    fun updateData(items: List<C>) {
        val diffCallback = DiffCallback<C>(this.items, items)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items.clear()
        this.items.addAll(items)
        Log.i(TAG, "updateData: ${this.items}")
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        val inflater = LayoutInflater.from(parent.context)
        return BaseViewHolder(
            DataBindingUtil.inflate(
                inflater,
                getLayoutId(viewType),
                parent,
                false
            )
        )
    }

    abstract fun getLayoutId(viewType: Int): Int

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        bind(holder.binding, items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    abstract fun bind(binding: V, item: C)
}