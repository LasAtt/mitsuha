package io.serd.mitsuha.dao

import io.serd.mitsuha.domain.Tag
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository

object Tags : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 128)
    val description = varchar("description", 256)
}

interface TagRepository : CrudRepository<Tag, Int> {
    fun save(name: String, description: String): Tag
}

@Repository
class TagRepositoryImpl : TagRepository {
    private fun ResultRow.toTag() = Tag(
        id = this[Tags.id],
        name = this[Tags.name],
        description = this[Tags.description]
    )

    override fun findAll(): List<Tag> = Tags.selectAll().map { it.toTag() }

    override fun findOne(id: Int): Tag? = Tags.select { Tags.id eq id }.firstOrNull()?.toTag()

    override fun save(name: String, description: String): Tag {
        val id = Tags.insert {
            it[Tags.name] = name
            it[Tags.description] = description
        }[Tags.id]
        return Tag(id = id, name = name, description = description)
    }

    override fun save(value: Tag): Tag {
        Tags.update({ Tags.id eq value.id }) {
            it[name] = value.name
        }
        return value
    }

    override fun remove(value: Tag) {
        Tags.deleteWhere { Tags.id eq value.id }
    }

    override fun remove(id: Int) {
        Tags.deleteWhere { Tags.id eq id }
    }

}
