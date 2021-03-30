package com.example.bestsecret.di

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
        productRepository: ProductRepository
    ): GetAllProductsUseCase {
        return GetAllProductsUseCase(productRepository)
    }

    @Singleton
    @Provides
    fun provideGetProductByIdUseCase(
        productRepository: ProductRepository
    ): GetProductByIdUseCase {
        return GetProductByIdUseCase(productRepository)
    }

}