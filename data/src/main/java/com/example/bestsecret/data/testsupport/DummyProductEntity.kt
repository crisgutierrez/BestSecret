package com.example.bestsecret.data.testsupport

import com.example.bestsecret.data.db.model.ProductCacheEntity

val DUMMY_PRODUCT_ENTITY_1 = ProductCacheEntity(
    id = 1,
    name = "name",
    description = "description",
    brand = "brand",
    price = 25,
    currency = "€",
    discountPercentage = 20,
    image = "image",
    stock = 3,
    link = "link",
    type = "type"
)

val DUMMY_PRODUCT_ENTITY_1_MORE_STOCK = ProductCacheEntity(
    id = 1,
    name = "name",
    description = "description",
    brand = "brand",
    price = 25,
    currency = "€",
    discountPercentage = 20,
    image = "image",
    stock = 5,
    link = "link",
    type = "type"
)

val DUMMY_PRODUCT_ENTITY_LIST = List(5) {
        ProductCacheEntity(
        id = it,
        name = "name",
        description = "description",
        brand = "brand",
        price = 25,
        currency = "€",
        discountPercentage = 20,
        image = "image",
        stock = 5,
        link = "link",
        type = "type"
    )
}