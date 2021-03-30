package com.example.bestsecret.data.repository

import com.example.bestsecret.data.db.datasourceimpl.ProductLocalDataSource
import com.example.bestsecret.data.network.datasourceimpl.ProductNetworkDataSource
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductList
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

    override suspend fun getProducts(params: ProductQueryParam): Flow<DataState<ProductList>> = flow {
        emit(DataState.InProgress())
        try {
            // Todo add caching strategy.
            val productList = productNetworkDataSource.getAll(params)
            emit(DataState.Success(productList))
        } catch (e: Exception){
            emit(DataState.Failure<ProductList>(e))
        }
    }

    /**
     * Get product by id, first we check if the product exist locally if exist we return it otherwise we
     * request it to the BE.
     * This cache strategy is only for test, ideally if the product exist locally we should check
     * if the product has changed, and if it has changed we should request it to the BE.
     */
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