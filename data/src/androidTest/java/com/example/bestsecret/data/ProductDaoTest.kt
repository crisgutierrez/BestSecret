package com.example.bestsecret.data

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.bestsecret.data.db.AppDatabase
import com.example.bestsecret.data.db.dao.ProductDao
import com.example.bestsecret.data.testsupport.DUMMY_PRODUCT_ENTITY_1
import com.example.bestsecret.data.testsupport.DUMMY_PRODUCT_ENTITY_1_MORE_STOCK
import com.example.bestsecret.data.testsupport.DUMMY_PRODUCT_ENTITY_LIST
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
class ProductDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var productDao: ProductDao

    @Before
    fun setup() {
        database = Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                AppDatabase::class.java
        ).allowMainThreadQueries().build()
        productDao = database.productDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertProductItem() = runBlocking {
        productDao.insertUpdateProduct(DUMMY_PRODUCT_ENTITY_1)

        val storedProduct = productDao.getProductById(DUMMY_PRODUCT_ENTITY_1.id)

        assert(DUMMY_PRODUCT_ENTITY_1 == storedProduct)
    }

    @Test
    fun insertProductItem_updatingStock() = runBlocking {
        productDao.insertUpdateProduct(DUMMY_PRODUCT_ENTITY_1)
        productDao.insertUpdateProduct(DUMMY_PRODUCT_ENTITY_1_MORE_STOCK)

        val storedProduct = productDao.getProductById(DUMMY_PRODUCT_ENTITY_1.id)

        assert(DUMMY_PRODUCT_ENTITY_1 != storedProduct)
        assert(DUMMY_PRODUCT_ENTITY_1_MORE_STOCK == storedProduct)
        assert(DUMMY_PRODUCT_ENTITY_1_MORE_STOCK.stock == 5)
    }

    @Test
    fun insertListOfProducts() = runBlocking {
        productDao.insertAllProducts(DUMMY_PRODUCT_ENTITY_LIST)

        val storedProductList = productDao.getAllProducts()

        assert(storedProductList.size == DUMMY_PRODUCT_ENTITY_LIST.size)
    }

    @Test
    fun removeProductFromDB() = runBlocking {
        // Add product in DB and assert it is added
        productDao.insertUpdateProduct(DUMMY_PRODUCT_ENTITY_1)
        val storedProduct = productDao.getProductById(DUMMY_PRODUCT_ENTITY_1.id)
        assert(DUMMY_PRODUCT_ENTITY_1 == storedProduct)

        // Remove product from DB and assert it is no in DB anymore
        productDao.deleteProductById(DUMMY_PRODUCT_ENTITY_1.id)
        val productList = productDao.getAllProducts()
        assert(productList.isEmpty())

    }

    @Test
    fun clearProductsDB() = runBlocking {
        // Add a list of product to the DE and assert that the DB is not empty
        productDao.insertAllProducts(DUMMY_PRODUCT_ENTITY_LIST)
        val productList = productDao.getAllProducts()
        assert(productList.isNotEmpty())

        // Clear the DB and assert that is empty
        productDao.clearProductTable()
        val productListAfterClear = productDao.getAllProducts()
        assert(productListAfterClear.isEmpty())

    }

}