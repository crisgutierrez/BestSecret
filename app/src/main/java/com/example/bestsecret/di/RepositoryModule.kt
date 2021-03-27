package com.example.bestsecret.di

import com.example.bestsecret.data.db.dao.ProductDao
import com.example.bestsecret.data.db.datasourceimpl.ProductLocalDataSource
import com.example.bestsecret.data.db.mapper.ProductCacheMapper
import com.example.bestsecret.data.network.datasourceimpl.ProductNetworkDataSource
import com.example.bestsecret.data.network.mapper.ProductNetworkMapper
import com.example.bestsecret.data.network.service.ProductService
import com.example.bestsecret.data.repository.ProductRepositoryImpl
import com.example.bestsecret.domain.datasource.DataSource
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductQueryParam
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
    ): DataSource<Product, Unit, ProductQueryParam, Unit, Int> {
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
    fun provideProductRepository(
        productLocalDataSource: ProductLocalDataSource,
        productNetworkDataSource: ProductNetworkDataSource
    ): ProductRepository {
        return ProductRepositoryImpl(productLocalDataSource, productNetworkDataSource)
    }


}