package com.example.base.api

import com.example.base.models.ErrorData
import org.json.JSONObject
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object HandleApiError {
    private const val somethingErrorMessage = "Something error please try again"
    private const val networkErrorMessage = "No internet connection"
    private const val serverErrorMessage = "Server error"
    private const val disconnectServerMessage = "Cannot connect to service "
    private const val somethingErrorCode = 93
    private const val serverErrorCode = 90
    private const val connectServerErrorCode = 91
    private const val networkErrorCode = 30
    private val somethingError = ErrorData(somethingErrorMessage, somethingErrorCode)

    fun handleError(throwable: Throwable): ErrorData {
        try {
            when (throwable) {
                is HttpException -> {
                    throwable.response()?.let { response ->
                        val errorBody = response.errorBody()?.string() ?: "{}"
                        val jsonBody = JSONObject(errorBody)
                        val statusCode = jsonBody.optInt("statusCode")
                        val errors = jsonBody.optJSONArray("errors")
                        var mess = errors?.optJSONObject(0)?.optString("errorMessage")
                        if (mess.isNullOrEmpty()) mess = throwable.message
                        return if (statusCode == 500) {
                            ErrorData(serverErrorMessage, serverErrorCode)
                        } else {
                            ErrorData(mess ?: somethingErrorMessage, statusCode)
                        }
                    }
                    return somethingError
                }
                is SocketTimeoutException -> {
                    return ErrorData(disconnectServerMessage, connectServerErrorCode)
                }
                is ConnectException -> {
                    return ErrorData(disconnectServerMessage, connectServerErrorCode)
                }
                is UnknownHostException -> {
                    return ErrorData(networkErrorMessage, networkErrorCode)
                }
                else -> {
                    return somethingError
                }
            }
        } catch (e: Exception) {
            return somethingError
        }
    }
}