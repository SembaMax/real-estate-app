package com.semba.realestateapp.data.model

sealed class DataResponse<T> (
    val data: T? = null,
    val errorCode: Int? = null
)
{
    class Success<T>(data: T) : DataResponse<T>(data = data, errorCode = null)
    class Failure<T>(errorCode: Int?) : DataResponse<T>(data = null, errorCode = errorCode)
}
