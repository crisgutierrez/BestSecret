package com.example.bestsecret.domain.repository

import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductList
import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.state.DataState
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(params: ProductQueryParam): Flow<DataState<ProductList>>

    suspend fun getProductById(params: ProductQueryParam): Flow<DataState<Product>>
}