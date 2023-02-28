package com.example.base.ui.main

import com.example.base.api.ApiService
import com.example.base.base.BaseRepository
import com.example.base.di.IoDispatcher
import com.example.base.models.BaseResult
import com.example.base.models.ListResult
import com.example.base.models.Listing
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListingRepository @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val apiService: ApiService
) :
    BaseRepository(ioDispatcher) {
    suspend fun getListTrending(
        page: Int,
        limit: Int
    ): Flow<BaseResult<ListResult<Listing>>> =
        getResult { apiService.getTrending(page, limit) }

}