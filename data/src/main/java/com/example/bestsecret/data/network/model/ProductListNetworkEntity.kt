package com.example.bestsecret.data.network.model

data class ProductListNetworkEntity(
    val list: List<ProductNetworkEntity>,
    val page: Int,
    val pageSize: Int,
    val size: Int,
    val _link: String,
    val _type: String,
    val _next: String?
)
