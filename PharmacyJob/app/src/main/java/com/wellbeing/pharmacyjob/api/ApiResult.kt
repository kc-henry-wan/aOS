package com.wellbeing.pharmacyjob.api

sealed class ApiResult<T>(
    val data: T? = null,
    val httpStatusCode: Int? = null,          // HTTP Status Code
    val errorCode: String? = null, // Backend-specific Error Code
    val errorMessage: String? = null,
    val rawResponse: String? = null
) {
    class Success<T>(data: T) : ApiResult<T>(data)
    class Error<T>(
        httpStatusCode: Int?,
        errorCode: String?,
        errorMessage: String,
        data: T? = null,
        rawResponse: String? = null
    ) : ApiResult<T>(data, httpStatusCode, errorCode, errorMessage, rawResponse)
}
