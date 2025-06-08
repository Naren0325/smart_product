package org.saneforce.productmanager.api

import org.saneforce.productmanager.common.ApiError
import org.saneforce.productmanager.common.ApiResult
import com.iggloo.hrs.b2b.common.parseErrorMessage
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.json.Json
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.saneforce.productmanager.model.Product
import org.saneforce.productmanager.model.ProductForSave
import org.saneforce.productmanager.model.ProductSaveRequest


class ProductApiImpl :  KoinComponent {

    private val httpClient: HttpClient by inject()

    suspend fun getProducts(): ApiResult<List<Product>> {
        return try {
            val response: HttpResponse = httpClient.get {
                url("http://sjdev.salesjump.in/server/native_Db_V13.php?axn=get/taskproducts&divisionCode=258")
                header("token",ContentType.Application.Json)
                contentType(ContentType.Application.Json)
            }

            val jsonString = response.bodyAsText()
            println("Response JSON: $jsonString")

            val body = Json.decodeFromString<List<Product>>(jsonString)
            ApiResult.Success(body)

        } catch (e: Exception) {
            ApiResult.Failure(ApiError(1500, "An error occurred: ${e.message}"))
        }
    }

    suspend fun saveProducts(products: List<ProductForSave>): ApiResult<Unit> {
        return try {
            val response: HttpResponse = httpClient.post {
                url("http://sjdev.salesjump.in/server/native_Db_V13.php?axn=save/taskproddets&divisionCode=258")
                contentType(ContentType.Application.Json)
                setBody(ProductSaveRequest(data = products))
            }

            if (response.status.isSuccess()) {
                ApiResult.Success(Unit)
            } else {
                val errorText = response.bodyAsText()
                val errorCode = response.status.value
                ApiResult.Failure(ApiError(errorCode, parseErrorMessage(errorText)))
            }
        } catch (e: Exception) {
            ApiResult.Failure(ApiError(1500, "An error occurred: ${e.message}"))
        }
    }

}