package com.example.base.models

sealed class BaseResult<out R> {
    data class Success<T>(val data: T) : BaseResult<T>()
    object Loading : BaseResult<Nothing>()
    data class Error(val errorData: ErrorData) : BaseResult<Nothing>()
}