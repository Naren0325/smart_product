package org.saneforce.productmanager.common

data class ApiError(val code: Int, val message: String) {

    fun toMessage() = "$code : $message"
}
