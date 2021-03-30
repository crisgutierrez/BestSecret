package com.example.bestsecret.domain.model

data class Product(
    val id: Int,
    val name: String,
    val description: String?,
    val brand: String,
    val price: Int,
    val currency: String,
    val discountPercentage: Int,
    val image: String,
    val stock: Int,
    val link: String,
    val type: String
) {
    override fun equals(other: Any?): Boolean {
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Product

        if (id != other.id) {
            return false
        }
        if (name != other.name) {
            return false
        }
        if (description != other.description) {
            return false
        }
        if (brand != other.brand) {
            return false
        }
        if (price != other.price) {
            return false
        }
        if (currency != other.currency) {
            return false
        }
        if (discountPercentage != other.discountPercentage) {
            return false
        }
        if (image != other.image) {
            return false
        }
        if (stock != other.stock) {
            return false
        }
        if (link != other.link) {
            return false
        }
        if (type != other.type) {
            return false
        }
        return true
    }
}

sealed class ProductQueryParam {

    data class QueryAllProducts(
        val page: Int,
        val pageSize: Int
    ): ProductQueryParam()

    data class QueryProductById(
        val productId: Int
    ): ProductQueryParam()
}
