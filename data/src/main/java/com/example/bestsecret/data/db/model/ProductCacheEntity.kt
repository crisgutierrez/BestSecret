package com.example.bestsecret.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bestsecret.data.db.model.ProductCacheEntity.Companion.TABLE_NAME

@Entity(tableName = TABLE_NAME)
data class ProductCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "brand")
    val brand: String,

    @ColumnInfo(name = "price")
    val price: Int,

    @ColumnInfo(name = "currency")
    val currency: String,

    @ColumnInfo(name = "discountPercentage")
    val discountPercentage: Int,

    @ColumnInfo(name = "image")
    val image: String,

    @ColumnInfo(name = "stock")
    val stock: Int,

    @ColumnInfo(name = "link")
    val link: String,

    @ColumnInfo(name = "type")
    val type: String){

    companion object {
        const val TABLE_NAME = "Product"
    }

}
