package com.example.base.ui.main

import com.example.base.base.BaseListViewModel
import com.example.base.models.BaseResult
import com.example.base.models.ListResult
import com.example.base.models.Listing
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val listingRepository: ListingRepository) :
    BaseListViewModel<Listing>() {
    override suspend fun getDataFromRepository(): Flow<BaseResult<ListResult<Listing>>> {
        return listingRepository.getListTrending(page, limit)
    }
}