package com.example.bestsecret.domain.usecase

import com.example.bestsecret.domain.model.ProductQueryParam
import com.example.bestsecret.domain.repository.ProductListRepository
import javax.inject.Inject

class GetAllProductsUseCase
@Inject
constructor(
    private val productListRepository: ProductListRepository
){
    suspend fun getProducts(params: ProductQueryParam) = productListRepository.getProducts(params)

}