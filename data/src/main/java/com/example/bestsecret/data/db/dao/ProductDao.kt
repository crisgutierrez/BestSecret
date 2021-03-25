package com.example.bestsecret.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bestsecret.data.db.model.ProductCacheEntity

@Dao
interface ProductDao {

    //INSERT or UPDATE if exists
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUpdateProduct(AlertEntity: ProductCacheEntity)


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