package org.saneforce.productmanager.ui.viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import co.touchlab.kermit.Logger
import org.saneforce.productmanager.common.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.saneforce.productmanager.api.ProductApiImpl
import org.saneforce.productmanager.model.Product
import org.saneforce.productmanager.model.ProductForSave
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.saneforce.productmanager.ui.ProductState

class ProductViewModel : ScreenModel, KoinComponent {

    private val productApi by inject<ProductApiImpl>()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> get() = _products

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> get() = _loading

    private val _state = MutableStateFlow(ProductState.LOADING)
    val state: StateFlow<ProductState> = _state

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error

    private val mutex = Mutex()

    fun loadProducts() {
        screenModelScope.launch(Dispatchers.IO) {
            mutex.withLock {
                _state.value = ProductState.LOADING
                when (val result = productApi.getProducts()) {
                    is ApiResult.Success -> {
                        _state.value = ProductState.SUCCESS
                        _products.value = result.value.map {
                            it.copy(quantity = 1, rate = 0.0)
                        }
                    }
                    is ApiResult.Failure -> {
                        _state.value = ProductState.ERROR
                        _error.value = result.error.message
                       Logger.d("Error Loading: ${result.error.code} ${result.error.message}")
                    }
                }
                _state.value = ProductState.FAILURE
            }
        }
    }

    fun updateQuantity(productCode: String, newQuantity: Int) {
        _products.value = _products.value.map { product ->
            if (product.productCode == productCode) {
                product.copy(quantity = newQuantity.coerceAtLeast(1))
            } else {
                product
            }
        }
    }

    fun deleteProduct(productCode: String) {
        _products.value = _products.value.filterNot {
            it.productCode == productCode
        }
    }

    fun updateRate(productCode: String, newRate: String) {
        _products.value = _products.value.map { product ->
            if (product.productCode == productCode) {
                product.copy(
                    rate = newRate.toDoubleOrNull() ?: 0.0
                )
            } else {
                product
            }
        }
    }


    fun saveProducts() {
        screenModelScope.launch(Dispatchers.IO) {
            mutex.withLock {
                _state.value = ProductState.LOADING
                val productsToSave = _products.value.map { product ->
                    ProductForSave(
                        productCode = product.productCode,
                        productName = product.productName,
                        quantity = product.quantity,
                        rate = product.rate,
                        amount = product.total
                    )
                }
                Logger.d("Products to save: ${Json.encodeToString(productsToSave)}")

                when (val result = productApi.saveProducts(productsToSave)) {
                    is ApiResult.Success -> {
                        _state.value = ProductState.SUCCESS
                        Logger.d("Success Value is True")
                    }

                    is ApiResult.Failure -> {
                        _state.value = ProductState.ERROR
                        _error.value = result.error.message
                    }
                }
                _state.value = ProductState.FAILURE
            }
        }
    }
}