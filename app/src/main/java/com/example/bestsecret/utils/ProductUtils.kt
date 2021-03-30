package com.example.bestsecret.utils

const val PRODUCTS_PAGE_SIZE = 5

fun getPriceWithDiscount(price: Int, discount: Int): String {
    val priceWithDiscount = (price.toDouble() * (100 - discount) / 100)
    return formatNumberToTwoDigits(priceWithDiscount)
}

fun formatNumberToTwoDigits(number: Double): String {
    return String.format("%.2f", number)
}