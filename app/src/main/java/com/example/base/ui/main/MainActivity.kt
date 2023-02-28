package com.example.base.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.commit
import com.example.base.R
import com.example.base.base.BaseActivity
import com.example.base.base.BaseViewModel
import com.example.base.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val viewModel by viewModels<BaseViewModel>()
    override val layoutId = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.commit {
            replace(viewBinding.layoutFragment.id, FragmentMain.getInstance())
        }
    }

    companion object {
        fun startIntent(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }
}