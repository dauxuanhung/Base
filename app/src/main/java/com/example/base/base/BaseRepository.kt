package com.example.base.base

import com.example.base.api.HandleApiError
import com.example.base.models.BaseResult
import com.example.base.models.ErrorData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*

open class BaseRepository(private val coroutineDispatcher: CoroutineDispatcher) {

    suspend fun <R> getResult(call: suspend () -> BaseResponseApi<R>): Flow<BaseResult<R>> {
        return flow {
            val result = call.invoke()
            emit(
                if (result.success) {
                    if (result.data == null) {
                        BaseResult.Error(ErrorData("Parse data error", 1))
                    } else {
                        BaseResult.Success(result.data)
                    }
                } else {
                    BaseResult.Error(ErrorData("error", 1))
                }
            )
        }.onStart {
            emit(BaseResult.Loading)
        }.catch { e ->
            emit(BaseResult.Error(HandleApiError.handleError(throwable = e)))
        }.flowOn(coroutineDispatcher)
    }

}