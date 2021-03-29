package com.example.bestsecret.di

import com.example.bestsecret.data.db.dao.ProductDao
import com.example.bestsecret.data.db.datasourceimpl.ProductLocalDataSource
import com.example.bestsecret.data.db.mapper.ProductCacheMapper
import com.example.bestsecret.data.network.datasourceimpl.ProductListNetworkDataSource
import com.example.bestsecret.data.network.datasourceimpl.ProductNetworkDataSource
import com.example.bestsecret.data.network.mapper.ProductListNetworkMapper
import com.example.bestsecret.data.network.mapper.ProductNetworkMapper
import com.example.bestsecret.data.network.service.ProductService
import com.example.bestsecret.data.repository.ProductListRepositoryImpl
import com.example.bestsecret.data.repository.ProductRepositoryImpl
import com.example.bestsecret.domain.datasource.DataSource
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductList
import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.repository.ProductListRepository
import com.example.bestsecret.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideProductLocalDataSource(
        productDao: ProductDao,
        productCacheMapper: ProductCacheMapper
    ): DataSource<Product?, Unit, ProductQueryParam, Unit, Int> {
        return ProductLocalDataSource(productDao, productCacheMapper)
    }

    @Singleton
    @Provides
    fun provideProductNetworkDataSource(
        productService: ProductService,
        productNetworkMapper: ProductNetworkMapper
    ): DataSource<Product?, Unit, ProductQueryParam, Unit, Unit> {
        return ProductNetworkDataSource(productService, productNetworkMapper)
    }

    @Singleton
    @Provides
    fun provideProductListNetworkDataSource(
        productService: ProductService,
        productListNetworkMapper: ProductListNetworkMapper
    ): DataSource<ProductList?, Unit, ProductQueryParam, Unit, Unit> {
        return ProductListNetworkDataSource(productService, productListNetworkMapper)
    }

    @Singleton
    @Provides
    fun provideProductRepository(
        productLocalDataSource: ProductLocalDataSource,
        productNetworkDataSource: ProductNetworkDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(productLocalDataSource, productNetworkDataSource)
    }

    @Singleton
    @Provides
    fun provideProductListRepositoryImpl(
        productLocalDataSource: ProductLocalDataSource,
        productListNetworkDataSource: ProductListNetworkDataSource
    ): ProductListRepository {
        return ProductListRepositoryImpl(productLocalDataSource, productListNetworkDataSource)
    }


}