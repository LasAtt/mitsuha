package io.serd.mitsuha.services

import io.serd.mitsuha.dao.CrudRepository
import io.serd.mitsuha.exceptions.NoSuchEntityException
import org.springframework.transaction.annotation.Transactional
import java.io.Serializable

interface CrudService<T, in ID: Serializable> {
    fun findOne(id: ID): T?
    fun findAll(): List<T>

    fun update(value: T): T
    fun delete(value: T)
    fun delete(id: ID)
}

@Transactional
abstract class AbstractCrudService<T, in ID: Serializable> : CrudService<T, ID> {
    abstract val repository: CrudRepository<T, ID>

    override fun findOne(id: ID) = repository.findOne(id)

    override fun findAll() = repository.findAll()

    override fun update(value: T) = repository.save(value)

    override fun delete(value: T) = repository.remove(value)

    override fun delete(id: ID) = repository.remove(id)

    protected fun findOneOrThrow(id: ID) = findOne(id) ?: throw NoSuchEntityException("No entity with id $id exists!")

}
