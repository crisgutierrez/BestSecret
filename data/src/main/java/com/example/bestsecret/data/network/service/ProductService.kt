package com.example.bestsecret.data.network.service

import com.example.bestsecret.data.network.model.ProductListNetworkEntity
import com.example.bestsecret.data.network.model.ProductNetworkEntity
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {

    companion object {
        private const val PRODUCT_ID_PARAM = "productId"
        private const val PRODUCT_QUERY_PAGE = "page"
        private const val PRODUCT_QUERY_PAGE_SIZE = "pageSize"

        const val PRODUCT_PATH =  "products"
        const val ALERT_BY_ID_PATH =  "${PRODUCT_PATH}/{$PRODUCT_ID_PARAM}"
    }

    @GET(PRODUCT_PATH)
    suspend fun getAllProducts(
        @Query(PRODUCT_QUERY_PAGE) page: Int,
        @Query(PRODUCT_QUERY_PAGE_SIZE) pageSize: Int
    ): ProductListNetworkEntity

    @GET(ALERT_BY_ID_PATH)
    suspend fun getProductById(
        @Path(PRODUCT_ID_PARAM) productId: Int
    ): ProductNetworkEntity
}