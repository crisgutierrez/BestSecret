package com.example.bestsecret.data.network.datasourceimpl

import com.example.bestsecret.data.network.mapper.ProductListNetworkMapper
import com.example.bestsecret.data.network.service.ProductService
import com.example.bestsecret.domain.datasource.DataSource
import com.example.bestsecret.domain.model.ProductList
import com.example.bestsecret.domain.model.ProductQueryParam
import javax.inject.Inject

class ProductListNetworkDataSource
@Inject
constructor(
    private val productService: ProductService,
    private val productListNetworkMapper: ProductListNetworkMapper
): DataSource<ProductList?, Unit, ProductQueryParam, Unit, Unit> {
    override suspend fun create(params: Unit): ProductList? {
        TODO("Not yet implemented")
    }

    override suspend fun add(item: ProductList?): ProductList? {
        TODO("Not yet implemented")
    }

    override suspend fun addAll(all: List<ProductList?>): List<ProductList?>? {
        TODO("Not yet implemented")
    }

    override suspend fun get(params: ProductQueryParam): ProductList? {
        return if (params is ProductQueryParam.QueryAllProducts) {
            val networkProduct = productService.getAllProducts(params.page, params.pageSize)
            productListNetworkMapper.toModel(networkProduct)
        } else null
    }

    override suspend fun getAll(params: ProductQueryParam): List<ProductList?>? {
        TODO("Not yet implemented")
    }

    override suspend fun update(params: Unit): ProductList? {
        TODO("Not yet implemented")
    }

    override suspend fun delete(params: Unit) {
        TODO("Not yet implemented")
    }

    override suspend fun replace(item: ProductList?) {
        TODO("Not yet implemented")
    }

    override suspend fun replaceAll(all: List<ProductList?>) {
        TODO("Not yet implemented")
    }

    override suspend fun clear() {
        TODO("Not yet implemented")
    }
}