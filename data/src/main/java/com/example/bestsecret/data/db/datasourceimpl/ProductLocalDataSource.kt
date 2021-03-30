package com.example.bestsecret.data.db.datasourceimpl

import com.example.bestsecret.data.db.dao.ProductDao
import com.example.bestsecret.data.db.mapper.ProductCacheMapper
import com.example.bestsecret.domain.datasource.DataSource
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductList
import com.example.bestsecret.domain.model.ProductQueryParam
import javax.inject.Inject

class ProductLocalDataSource
@Inject
constructor(
    private val productDao: ProductDao,
    private val productCacheMapper: ProductCacheMapper
): DataSource<Product?, ProductList?, Unit, ProductQueryParam, Unit, Int> {
    override suspend fun create(params: Unit): Product {
        TODO("Not yet implemented")
    }

    override suspend fun add(item: Product?): Product? {
        if (item != null) {
            val productEntity = productCacheMapper.toEntity(item)
            productDao.insertUpdateProduct(productEntity)
        }
        return item
    }

    override suspend fun addAll(all: List<Product?>): List<Product?> {
        TODO("Not yet implemented")
    }

    override suspend fun get(params: ProductQueryParam): Product? {
        return when(params) {
            is ProductQueryParam.QueryProductById -> {
                val productEntity = productDao.getProductById(params.productId)
                if (productEntity != null) {
                    productCacheMapper.toModel(productEntity)
                } else null
            }
            else -> null
        }
    }

    override suspend fun getAll(params: ProductQueryParam): ProductList? {
        TODO("Not yet implemented")
    }

    override suspend fun update(params: Unit): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(params: Int) {
        productDao.deleteProductById(params)
    }

    override suspend fun replace(item: Product?) {
        if (item != null) {
            val productEntity = productCacheMapper.toEntity(item)
            productDao.insertUpdateProduct(productEntity)
        }
    }

    override suspend fun replaceAll(all: List<Product?>) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        productDao.clearProductTable()
    }
}