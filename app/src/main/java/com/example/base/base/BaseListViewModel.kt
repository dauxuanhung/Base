package com.example.base.base

import androidx.lifecycle.viewModelScope
import com.example.base.models.BaseResult
import com.example.base.models.ListResult
import com.example.base.utils.safeLaunch
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseListViewModel<Item : Any> : BaseViewModel() {
    protected var page = 1
    protected val limit = 10

    private val _listState = MutableStateFlow(ListState<Item>())
    private var job: Job? = null
    val listState = _listState.asStateFlow()

    data class ListState<Item>(
        val loading: Boolean = false,
        val error: String? = null,
        val listData: BaseListState<Item>? = null,
        val totalSize: Int = 0
    )

    abstract suspend fun getDataFromRepository(): Flow<BaseResult<ListResult<Item>>>

    fun updateEmptyState() {
        _listState.value = ListState(listData = BaseListState(emptyList = true))
    }

    fun loadListData(loadMore: Boolean = false) {
        job?.cancel()
        if (loadMore) {
            page++
        } else {
            page = 1
        }
        job = viewModelScope.safeLaunch {
            block = {
                getDataFromRepository().collect { result ->
                    when (result) {
                        is BaseResult.Loading -> {
                            if (page == 1) {
                                _listState.value = ListState(loading = true)
                            }
                        }
                        is BaseResult.Success -> {
                            val listResult = result.data
                            if (listResult.total == 0) {
                                _listState.value =
                                    ListState(listData = BaseListState(emptyList = true))
                            } else {
                                _listState.value = ListState(
                                    listData = BaseListState(
                                        data = listResult.data,
                                        endList = listResult.data.size < limit
                                    ), totalSize = result.data.total
                                )
                            }
                        }
                        is BaseResult.Error -> {
                            _listState.value = ListState(error = result.errorData.text)
                        }
                    }
                }
            }
        }
    }
}