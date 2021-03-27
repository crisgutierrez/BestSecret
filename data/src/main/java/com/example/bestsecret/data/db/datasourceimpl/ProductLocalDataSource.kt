package com.example.bestsecret.data.db.datasourceimpl

import com.example.bestsecret.data.db.dao.ProductDao
import com.example.bestsecret.data.db.mapper.ProductCacheMapper
import com.example.bestsecret.domain.datasource.DataSource
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductQueryParam
import javax.inject.Inject

class ProductLocalDataSource
@Inject
constructor(
    private val productDao: ProductDao,
    private val productCacheMapper: ProductCacheMapper
): DataSource<Product, Unit, ProductQueryParam, Unit, Int> {
    override suspend fun create(params: Unit): Product {
        TODO("Not yet implemented")
    }

    override suspend fun add(item: Product): Product {
        val productEntity = productCacheMapper.toEntity(item)
        productDao.insertUpdateProduct(productEntity)
        return item
    }

    override suspend fun addAll(all: List<Product>): List<Product> {
        val productEntityList = productCacheMapper.toEntityList(all)
        productDao.insertAllProducts(productEntityList)
        return all
    }

    override suspend fun get(params: ProductQueryParam): Product {
        when(params) {
            is ProductQueryParam.QueryProductById -> {
                val productEntity = productDao.getProductById(params.productId)
                return productCacheMapper.toModel(productEntity)
            }
            else -> TODO("Not yet implemented")
        }
    }

    override suspend fun getAll(params: ProductQueryParam): List<Product> {
        when(params) {
            is ProductQueryParam.QueryAllProducts -> {
                val productEntityList = productDao.getAllProducts()
                return productCacheMapper.toModelList(productEntityList)
            }
            else -> TODO("Not yet implemented")
        }
    }

    override suspend fun update(params: Unit): Product {
        TODO("Not yet implemented")
    }

    override suspend fun delete(params: Int) {
        productDao.deleteProductById(params)
    }

    override suspend fun replace(item: Product) {
        val productEntity = productCacheMapper.toEntity(item)
        productDao.insertUpdateProduct(productEntity)
    }

    override suspend fun replaceAll(all: List<Product>) {
        val productEntityList = productCacheMapper.toEntityList(all)
        productDao.replaceAllActionTaskList(productEntityList)
    }

    override suspend fun clear() {
        productDao.clearProductTable()
    }
}