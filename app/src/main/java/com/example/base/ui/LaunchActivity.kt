package com.example.base.ui

import android.os.Bundle
import androidx.activity.viewModels
import com.example.base.R
import com.example.base.base.BaseActivity
import com.example.base.databinding.ActivityLaunchBinding
import com.example.base.ui.launch.LaunchViewModel
import com.example.base.ui.main.MainActivity
import com.example.base.utils.dismissLLoadingDialog
import com.example.base.utils.launchCoroutineWhenStarted
import com.example.base.utils.showDialog
import com.example.base.utils.showLoadingDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LaunchActivity : BaseActivity<ActivityLaunchBinding>() {

    override val viewModel by viewModels<LaunchViewModel>()

    override val layoutId = R.layout.activity_launch

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding.login.setOnClickListener {
            viewModel.login(viewBinding.email.text.toString(), viewBinding.password.text.toString())
        }
        launchCoroutineWhenStarted {
            launch {
                viewModel.loginUiState.collect { state ->
                    if (state.loading) {
                        showLoadingDialog()
                    }
                    if (state.success) {
                        dismissLLoadingDialog()
                    }
                    if (state.error != null) {
                        dismissLLoadingDialog()
                        showDialog(message = state.error)
                    }
                }

            }
            launch {
                viewModel.isLogin.collect { isLogin ->
                    if (isLogin) {
                        MainActivity.startIntent(this@LaunchActivity)
                        finish()
                    }
                }
            }
        }

    }
}