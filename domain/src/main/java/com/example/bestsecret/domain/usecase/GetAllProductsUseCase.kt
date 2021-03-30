package com.example.bestsecret.domain.usecase

import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.repository.ProductRepository
import javax.inject.Inject

class GetAllProductsUseCase
@Inject
constructor(
    private val productRepository: ProductRepository
){
    suspend fun getProducts(params: ProductQueryParam) = productRepository.getProducts(params)

}