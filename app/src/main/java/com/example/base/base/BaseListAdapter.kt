package com.example.base.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncDifferConfig
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import java.util.concurrent.Executors

private interface BaseRecyclerAdapter<Item : Any, ViewBinding : ViewDataBinding> {

    fun getLayoutRes(viewType: Int): Int

    fun bindView(binding: ViewBinding, item: Item, position: Int) {}

}

abstract class BaseListAdapter<Item : Any, ViewBinding : ViewDataBinding>(
    callBack: DiffUtil.ItemCallback<Item>
) : ListAdapter<Item, BaseViewHolder<ViewBinding>>(
    AsyncDifferConfig.Builder(callBack)
        .setBackgroundThreadExecutor(Executors.newSingleThreadExecutor())
        .build()
), BaseRecyclerAdapter<Item, ViewBinding> {

    override fun submitList(list: List<Item>?) {
        super.submitList(ArrayList<Item>(list ?: listOf()))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<ViewBinding> {
        return BaseViewHolder(DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            getLayoutRes(viewType),
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<ViewBinding>, position: Int) {
        val item: Item? = getItem(position)

    }
}

open class BaseViewHolder<ViewBinding : ViewDataBinding>(
    val binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root)
