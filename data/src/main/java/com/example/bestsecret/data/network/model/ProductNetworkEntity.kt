package com.example.bestsecret.data.network.model

data class ProductNetworkEntity(
    val id: Int,
    val name: String,
    val description: String,
    val brand: String,
    val price: Int,
    val currency: String,
    val discountPercentage: Int,
    val image: String,
    val stock: Int,
    val link: String,
    val type: String
    )
