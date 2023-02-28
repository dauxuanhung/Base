package com.example.base.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.base.R
import com.example.base.base.BaseListFragment
import com.example.base.databinding.ItemListingBinding
import com.example.base.models.Listing

class FragmentMain : BaseListFragment<Listing, ItemListingBinding>() {
    override val emptyViewLayoutResourceId = R.layout.layout_empty_listing
    override val listAdapter = ListingAdapter()
    override val viewModel by viewModels<MainViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        fun getInstance() = FragmentMain()
    }
}