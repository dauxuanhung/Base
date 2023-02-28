package com.example.base.ui.main

import androidx.recyclerview.widget.DiffUtil
import com.example.base.R
import com.example.base.base.BaseListAdapter
import com.example.base.databinding.ItemListingBinding
import com.example.base.models.Listing

class ListingAdapter : BaseListAdapter<Listing, ItemListingBinding>(ListingDiffCallback()) {
    override fun getLayoutRes(viewType: Int) = R.layout.item_listing

    class ListingDiffCallback : DiffUtil.ItemCallback<Listing>() {
        override fun areItemsTheSame(oldItem: Listing, newItem: Listing): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Listing, newItem: Listing): Boolean {
            return oldItem.address.address1 == newItem.address.address1
        }
    }

}