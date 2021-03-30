package com.example.bestsecret.data.network.model

data class ProductNetworkEntity(
    val id: Int,
    val name: String,
    val description: String?,
    val brand: String,
    val price: Int,
    val currency: String,
    val discountPercentage: Int,
    val image: String,
    val stock: Int,
    val _link: String,
    val _type: String
    )
