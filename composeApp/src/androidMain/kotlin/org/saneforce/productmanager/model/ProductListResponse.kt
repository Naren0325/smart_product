package org.saneforce.productmanager.model

import kotlinx.serialization.Serializable
import org.saneforce.productmanager.model.Product

@Serializable
data class ProductListResponse(
    val products: List<Product>
)
