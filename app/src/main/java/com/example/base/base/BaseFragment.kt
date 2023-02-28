package com.example.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.base.utils.dismissLLoadingDialog
import com.example.base.utils.showLoadingDialog
import kotlinx.coroutines.launch

abstract class BaseFragment<ViewBinding : ViewDataBinding> : Fragment() {
    protected lateinit var viewBinding: ViewBinding
    protected abstract val viewModel: BaseViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.loadingState.collect { showLoading ->
                if (showLoading) {
                    showLoadingDialog()
                } else {
                    dismissLLoadingDialog()
                }
            }
        }
    }
}