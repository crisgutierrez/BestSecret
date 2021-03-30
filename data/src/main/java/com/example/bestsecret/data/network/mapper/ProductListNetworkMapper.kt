package com.example.bestsecret.data.network.mapper

import com.example.bestsecret.data.network.model.ProductListNetworkEntity
import com.example.bestsecret.data.network.model.ProductNetworkEntity
import com.example.bestsecret.domain.mapper.EntityMapper
import com.example.bestsecret.domain.model.Product
import com.example.bestsecret.domain.model.ProductList
import javax.inject.Inject

class ProductListNetworkMapper
@Inject
constructor():EntityMapper<ProductList, ProductListNetworkEntity> {
    override fun toModel(entity: ProductListNetworkEntity): ProductList =
        ProductList(
            list = entity.list.map {
                Product(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    brand = it.brand,
                    price = it.price,
                    currency = it.currency,
                    discountPercentage = it.discountPercentage,
                    image = it.image,
                    stock = it.stock,
                    link = it._link,
                    type = it._type
                )
            }.toMutableList(),
            page = entity.page,
            pageSize = entity.pageSize,
            size = entity.size,
            link = entity._link,
            type = entity._type,
            next = entity._next
        )

    override fun toEntity(model: ProductList): ProductListNetworkEntity =
        ProductListNetworkEntity(
            list = model.list.map {
                ProductNetworkEntity(
                    id = it.id,
                    name = it.name,
                    description = it.description,
                    brand = it.brand,
                    price = it.price,
                    currency = it.currency,
                    discountPercentage = it.discountPercentage,
                    image = it.image,
                    stock = it.stock,
                    _link = it.link,
                    _type = it.type
                )
            },
            page = model.page,
            pageSize = model.pageSize,
            size = model.size,
            _link = model.link,
            _type = model.type,
            _next = model.next
        )
}