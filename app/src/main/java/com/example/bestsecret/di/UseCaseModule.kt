package com.example.bestsecret.di

import com.example.bestsecret.domain.repository.ProductListRepository
import com.example.bestsecret.domain.repository.ProductRepository
import com.example.bestsecret.domain.usecase.GetAllProductsUseCase
import com.example.bestsecret.domain.usecase.GetProductByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Singleton
    @Provides
    fun provideGetAllProductsUseCase(
        productListRepository: ProductListRepository
    ): GetAllProductsUseCase {
        return GetAllProductsUseCase(productListRepository)
    }

    @Singleton
    @Provides
    fun provideGetProductByIdUseCase(
        productRepository: ProductRepository
    ): GetProductByIdUseCase {
        return GetProductByIdUseCase(productRepository)
    }

}