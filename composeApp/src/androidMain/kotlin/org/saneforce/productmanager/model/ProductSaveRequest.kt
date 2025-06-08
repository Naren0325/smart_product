package org.saneforce.productmanager.model

import kotlinx.serialization.Serializable
import org.saneforce.productmanager.model.ProductForSave

@Serializable
data class ProductSaveRequest(
    val data: List<ProductForSave>
)