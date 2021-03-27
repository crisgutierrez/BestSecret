package com.example.bestsecret.domain.datasource

interface DataSource<T, CreationParams, QueryParams, UpdateParams, DeleteParams> {

    suspend fun create(params: CreationParams): T

    suspend fun add(item: T): T

    suspend fun addAll(all: List<T>): List<T>?

    suspend fun get(params: QueryParams): T

    suspend fun getAll(params: QueryParams): List<T>?

    suspend fun update(params: UpdateParams): T

    suspend fun delete(params: DeleteParams)

    suspend fun replace(item: T)

    suspend fun replaceAll(all: List<T>)

    suspend fun clear()

}