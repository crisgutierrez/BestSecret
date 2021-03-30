package com.example.bestsecret.data.network.datasourceimpl

import com.example.bestsecret.data.network.mapper.ProductListNetworkMapper
import com.example.bestsecret.data.network.mapper.ProductNetworkMapper
import com.example.bestsecret.data.network.service.ProductService
import com.example.bestsecret.domain.datasource.DataSource
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductList
import com.example.bestsecret.domain.model.ProductQueryParam
import javax.inject.Inject

class ProductNetworkDataSource
@Inject
constructor(
    private val productService: ProductService,
    private val productNetworkMapper: ProductNetworkMapper,
    private val productListNetworkMapper: ProductListNetworkMapper
): DataSource<Product?, ProductList?, Unit, ProductQueryParam, Unit, Unit> {
    override suspend fun create(params: Unit): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun add(item: Product?): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(all: List<Product?>): List<Product?>? {
        TODO("Not yet implemented")
    }

    override suspend fun get(params: ProductQueryParam): Product? {
        return if (params is ProductQueryParam.QueryProductById) {
            val networkProduct = productService.getProductById(params.productId)
            productNetworkMapper.toModel(networkProduct)
        } else null
    }

    override suspend fun getAll(params: ProductQueryParam): ProductList? {
        return if (params is ProductQueryParam.QueryAllProducts) {
            val networkProduct = productService.getAllProducts(params.page, params.pageSize)
            productListNetworkMapper.toModel(networkProduct)
        } else null
    }

    override suspend fun update(params: Unit): Product? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(params: Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun replace(item: Product?) {
        TODO("Not yet implemented")
    }

    override suspend fun replaceAll(all: List<Product?>) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}