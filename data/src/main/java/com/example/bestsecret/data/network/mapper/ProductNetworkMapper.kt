package com.example.bestsecret.data.network.mapper

import com.example.bestsecret.data.network.model.ProductNetworkEntity
import com.example.bestsecret.domain.mapper.EntityMapper
import com.example.bestsecret.domain.model.Product
import javax.inject.Inject

class ProductNetworkMapper
@Inject
constructor():EntityMapper<Product, ProductNetworkEntity> {
    override fun toModel(entity: ProductNetworkEntity): Product =
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

    override fun toEntity(model: Product): ProductNetworkEntity =
        ProductNetworkEntity(
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