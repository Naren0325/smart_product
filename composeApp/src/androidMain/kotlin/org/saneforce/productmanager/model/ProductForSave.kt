package org.saneforce.productmanager.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductForSave(
    @SerialName("product_code") val productCode: String,
    @SerialName("product_name") val productName: String,
    @SerialName("Product_Qty") val quantity: Int,
    @SerialName("Rate") val rate: Double,
    @SerialName("Product_Amount") val amount: Double
)
