package org.saneforce.productmanager.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Product(
    @SerialName("product_code") val productCode: String,
    @SerialName("product_name") val productName: String,
    @SerialName("product_unit") val productUnit: String? = null,
    @SerialName("convQty") val convQty: Int,
    var quantity: Int = 1,
    var rate: Double = 0.0,
){
    val total: Double
        get() = quantity * rate
}
