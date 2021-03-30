package com.example.bestsecret.data.repository

import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.repository.ProductRepository
import com.example.bestsecret.domain.state.DataState
import kotlinx.coroutines.flow.Flow

class FakeProductRepositoryImpl: ProductRepository {
    override suspend fun getProducts(params: ProductQueryParam): Flow<DataState<List<Product>>> {
        TODO("Not yet implemented")
    }

    override suspend fun getProductById(params: ProductQueryParam): Flow<DataState<Product>> {
        TODO("Not yet implemented")
    }
}