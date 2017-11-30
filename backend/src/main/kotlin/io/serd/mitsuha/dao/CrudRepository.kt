package io.serd.mitsuha.dao

import java.io.Serializable

interface CrudRepository<T, in ID: Serializable> {
    fun findOne(id: ID): T?

    fun findAll(): List<T>

    fun save(value: T): T

    fun remove(value: T)

    fun remove(id: ID)
}
