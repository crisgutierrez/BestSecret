package com.example.bestsecret.data.network.model

data class ProductListNetworkEntity(
    val list: List<ProductNetworkEntity>,
    val page: Int,
    val pageSize: Int,
    val size: Int,
    val link: String,
    val type: String,
    val next: String
)
