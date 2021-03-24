package com.example.bestsecret.data.network.service

import com.example.bestsecret.data.network.model.ProductListNetworkEntity
import com.example.bestsecret.data.network.model.ProductNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {

    companion object {
        private const val PRODUCT_ID_PARAM = "productId"

        const val PRODUCT_PATH =  "products"
        const val ALERT_BY_ID_PATH =  "${PRODUCT_PATH}/{$PRODUCT_ID_PARAM}"
    }

    @GET(PRODUCT_PATH)
    suspend fun getAllProducts(): ProductListNetworkEntity

    @GET(ALERT_BY_ID_PATH)
    suspend fun getProductById(
        @Path(PRODUCT_ID_PARAM) networkId: String
    ): ProductNetworkEntity
}