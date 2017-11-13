package io.serd.mitsuha.dao

import io.serd.mitsuha.domain.Extension
import io.serd.mitsuha.domain.Image
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository

object Images : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 128).nullable()
    val extension = varchar("extension", 32)
    val path = varchar("path", 32)
    val hash = long("hash")
}

interface ImageRepository : CrudRepository<Image, Int> {
    fun save(name: String, extension: Extension, path: String, hash: Long): Image
}

@Repository
class ImageRepositoryImpl : ImageRepository {
    override fun createTable() = SchemaUtils.create(Images)

    private fun ResultRow.toImage() = Image(
        id = this[Images.id],
        name = this[Images.name],
        extension = Extension.fromMimeType(this[Images.extension]),
        path = this[Images.path],
        hash = this[Images.hash]
    )

    override fun findAll(): List<Image> = Images.selectAll().map { it.toImage() }

    override fun findOne(id: Int): Image? = Images.select { Images.id eq id }.firstOrNull()?.toImage()

    override fun save(name: String, extension: Extension, path: String, hash: Long): Image {
        val id = Images.insert {
            it[Images.name] = name
            it[Images.extension] = extension.mimeType
            it[Images.path] = path
            it[Images.hash] = hash
        }[Images.id]
        return Image(id = id, name = name, extension = extension, path = path, hash = hash)
    }

    override fun save(value: Image): Image {
        Images.update({ Images.id eq value.id }) {
            it[name] = value.name
        }
        return value
    }

    override fun remove(value: Image) {
        Images.deleteWhere { Images.id eq value.id }
    }

    override fun remove(id: Int) {
        Images.deleteWhere { Images.id eq id }
    }

}
