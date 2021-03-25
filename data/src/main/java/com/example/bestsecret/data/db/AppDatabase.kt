package com.example.bestsecret.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bestsecret.data.db.dao.ProductDao
import com.example.bestsecret.data.db.model.ProductCacheEntity

@Database(entities = [ProductCacheEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun productDao(): ProductDao
}