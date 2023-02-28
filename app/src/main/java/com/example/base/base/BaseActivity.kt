package com.example.base.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.example.base.utils.dismissLLoadingDialog
import com.example.base.utils.showLoadingDialog
import kotlinx.coroutines.launch

abstract class BaseActivity<ViewBinding : ViewDataBinding> : AppCompatActivity() {
    protected lateinit var viewBinding: ViewBinding
    protected abstract val viewModel: BaseViewModel

    @get:LayoutRes
    protected abstract val layoutId: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = DataBindingUtil.setContentView(this, layoutId)
        viewBinding.lifecycleOwner = this
        setContentView(viewBinding.root)
        lifecycleScope.launch {
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