package io.serd.mitsuha.dao

import java.io.Serializable

interface CrudRepository<T, in I: Serializable> {
    fun createTable()

    fun findOne(id: I): T?

    fun findAll(): List<T>

    fun save(value: T): T

    fun remove(value: T)

    fun remove(id: I)
}
