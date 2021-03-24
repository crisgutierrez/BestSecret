package com.example.bestsecret.domain.model

data class ProductList(
    val list: List<Product>,
    val page: Int,
    val pageSize: Int,
    val size: Int,
    val link: String,
    val type: String,
    val next: String
)
