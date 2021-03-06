package com.example.bestsecret.data.testsupport

import com.example.bestsecret.domain.model.Product
import java.util.*

/**
 * Helper class to populate the list while the server response is 503
 *
 * TODO: Remove this class when the server is working again.
 */
object DummyProducts {

    val ITEMS: MutableList<Product> = ArrayList()

    private const val COUNT = 25

    init {
        // Add some sample products.
        for (i in 1..COUNT) {
            addItem(createDummyItem(i))
        }
    }

    private fun addItem(item: Product) {
        ITEMS.add(item)
    }

    private fun createDummyItem(position: Int): Product {
        return Product(
            id = position,
            name = "shirts $position",
            description = "This a our best seller causal shirt",
            brand = "Tommy Hilfiger",
            price = 80,
            currency = "€",
            discountPercentage = if (position % 3 == 0 ) 0 else (1..80).random(), // This is to test products with no discount
            image = "https://picsum.photos/id/$position/1920/1080",
            stock = if (position % 3 == 0 ) 0 else (1..30).random(), // This is to test products with no stock
            link = "http://bestsecret-recruitment-api.herokuapp.com/products/$position",
            type = "productDetails"

        )
    }
}