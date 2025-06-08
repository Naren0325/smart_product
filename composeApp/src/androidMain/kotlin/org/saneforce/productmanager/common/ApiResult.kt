package org.saneforce.productmanager.common

sealed class ApiResult<out Success> {
    data class Success<out Success>(val value: Success) : ApiResult<Success>()
    data class Failure(val error: ApiError) : ApiResult<Nothing>()
}
