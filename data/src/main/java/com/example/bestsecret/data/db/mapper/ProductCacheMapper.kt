package com.example.bestsecret.data.db.mapper

import com.example.bestsecret.data.db.model.ProductCacheEntity
import com.example.bestsecret.domain.mapper.EntityMapper
import com.example.bestsecret.domain.model.Product
import javax.inject.Inject

class ProductCacheMapper
@Inject
constructor():EntityMapper<Product, ProductCacheEntity> {
    override fun toModel(entity: ProductCacheEntity): Product =
        Product(
            id = entity.id,
            name = entity.name,
            description = entity.description,
            brand = entity.brand,
            price = entity.price,
            currency = entity.currency,
            discountPercentage = entity.discountPercentage,
            image = entity.image,
            stock = entity.stock,
            link = entity.link,
            type = entity.type
        )

    override fun toEntity(model: Product): ProductCacheEntity =
        ProductCacheEntity(
            id = model.id,
            name = model.name,
            description = model.description,
            brand = model.brand,
            price = model.price,
            currency = model.currency,
            discountPercentage = model.discountPercentage,
            image = model.image,
            stock = model.stock,
            link = model.link,
            type = model.type
        )
}