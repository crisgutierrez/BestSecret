package com.example.bestsecret.data.repository

import com.example.bestsecret.data.db.datasourceimpl.ProductLocalDataSource
import com.example.bestsecret.data.network.datasourceimpl.ProductNetworkDataSource
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.repository.ProductRepository
import com.example.bestsecret.domain.state.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProductRepositoryImpl
@Inject
constructor(
    private val productLocalDataSource: ProductLocalDataSource,
    private val productNetworkDataSource: ProductNetworkDataSource
): ProductRepository {

    override suspend fun getProductById(params: ProductQueryParam): Flow<DataState<Product>> = flow {
        emit(DataState.InProgress())
        try {
            val cachedProduct = productLocalDataSource.get(params)
            if (cachedProduct == null) {
                val product = productNetworkDataSource.get(params)
                if (product != null) {
                    productLocalDataSource.add(product)
                }
                emit(DataState.Success(product))
            } else {
                emit(DataState.Success(cachedProduct))
            }
        } catch (e: Exception){
            emit(DataState.Failure<Product>(e))
        }
    }

}