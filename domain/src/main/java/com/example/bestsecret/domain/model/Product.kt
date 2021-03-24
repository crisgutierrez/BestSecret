package com.example.bestsecret.domain.model

data class Product(
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
