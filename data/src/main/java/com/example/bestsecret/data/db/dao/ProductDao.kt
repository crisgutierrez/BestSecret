package com.example.bestsecret.data.db.dao

import androidx.room.*
import com.example.bestsecret.data.db.model.ProductCacheEntity

@Dao
interface ProductDao {

    //INSERT or UPDATE if exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateProduct(productCacheEntity: ProductCacheEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllProducts(productCacheEntityList: List<ProductCacheEntity>)

    @Transaction
    suspend fun replaceAllActionTaskList(productCacheEntityList: List<ProductCacheEntity>) {
        clearProductTable()
        insertAllProducts(productCacheEntityList)
    }


    //GET
    @Query("SELECT * FROM ${ProductCacheEntity.TABLE_NAME}")
    suspend fun getAllProducts(): List<ProductCacheEntity>

    @Query("SELECT * FROM ${ProductCacheEntity.TABLE_NAME} WHERE id LIKE :productId")
    suspend fun getProductById(productId: Int): ProductCacheEntity


    //DELETE
    @Query("DELETE FROM ${ProductCacheEntity.TABLE_NAME} WHERE id LIKE :productId")
    suspend fun deleteProductById(productId: Int)

    @Query("DELETE FROM ${ProductCacheEntity.TABLE_NAME}")
    suspend fun clearProductTable()

}