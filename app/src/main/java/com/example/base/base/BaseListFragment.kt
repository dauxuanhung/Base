package com.example.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.base.R
import com.example.base.databinding.FragmentBaseListBinding
import com.example.base.utils.launchCoroutineWhenStarted

abstract class BaseListFragment<Item : Any, ViewBinding : ViewDataBinding> :
    BaseFragment<FragmentBaseListBinding>() {

    abstract val emptyViewLayoutResourceId: Int?

    abstract val listAdapter: BaseListAdapter<Item, ViewBinding>

    override val layoutId = R.layout.fragment_base_list

    open val enableRefresh: Boolean = false

    abstract override val viewModel: BaseListViewModel<Item>

    var callBackList: CallBackList<Item>? = null

    interface CallBackList<Item> {
        fun setItemClickListener(item: Item) {}
        fun onReturnSizeListData(listSize: Int) {}
    }

    protected fun checkEmptyList() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyViewLayoutResourceId?.let {
            viewBinding.emptyView.addView(
                LayoutInflater.from(requireContext()).inflate(it, null, false)
            )
        }
        viewBinding.swipeRefreshLayout.isEnabled = enableRefresh
        viewBinding.recyclerView.adapter = listAdapter.apply {

        }
        viewBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            reloadList()
            viewBinding.swipeRefreshLayout.isRefreshing = false
        }
        viewModel.loadListData()
        launchCoroutineWhenStarted {
            viewModel.listState.collect { state ->
                if (state.loading) {
                    viewBinding.progressBar.visibility = View.VISIBLE
                }
                if (state.error != null) {

                    viewBinding.progressBar.visibility = View.GONE

                }
                if (state.listData != null) {
                    viewBinding.progressBar.visibility = View.GONE
                    if (state.listData.emptyList) {
                        viewBinding.emptyView.visibility = View.VISIBLE
                    } else {
                        viewBinding.emptyView.visibility = View.GONE

                        if (state.listData.endList) {

                        } else {

                        }
                    }
                    callBackList?.onReturnSizeListData(state.totalSize)
                }
            }
        }
    }

    protected fun reloadList() {

        viewBinding.emptyView.visibility = View.GONE
        viewModel.loadListData()
    }
}