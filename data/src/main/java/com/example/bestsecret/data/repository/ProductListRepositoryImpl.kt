package com.example.bestsecret.data.repository

import com.example.bestsecret.data.db.datasourceimpl.ProductLocalDataSource
import com.example.bestsecret.data.network.datasourceimpl.ProductListNetworkDataSource
import com.example.bestsecret.data.network.datasourceimpl.ProductNetworkDataSource
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductList
import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.repository.ProductListRepository
import com.example.bestsecret.domain.repository.ProductRepository
import com.example.bestsecret.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductListRepositoryImpl
@Inject
constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productListNetworkDataSource: ProductListNetworkDataSource
): ProductListRepository {

    override suspend fun getProducts(params: ProductQueryParam): Flow<DataState<ProductList>> = flow {
        emit(DataState.InProgress())
        try {
            // Todo add caching strategy.
            val productList = productListNetworkDataSource.get(params)
            emit(DataState.Success(productList))
        } catch (e: Exception){
            emit(DataState.Failure<ProductList>(e))
        }
    }

}