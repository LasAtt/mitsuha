package io.serd.mitsuha.dao

import io.serd.mitsuha.domain.Extension
import io.serd.mitsuha.domain.Image
import org.jetbrains.exposed.sql.*
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

object Images : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 256).nullable()
    val extension = varchar("extension", 32)
    val file = varchar("file", 256)
    val thumb = varchar("thumb", 256)
}

interface ImageRepository : CrudRepository<Image, Int>

@Repository
@Transactional
class ImageRepositoryImpl : ImageRepository {
    override fun createTable() = SchemaUtils.create(Images)

    private fun ResultRow.toImage() = Image(
        id = this[Images.id],
        name = this[Images.name],
        extension = Extension.fromMimeType(this[Images.extension]),
        file = this[Images.file],
        thumb = this[Images.thumb]
    )

    override fun findAll(): List<Image> = Images.selectAll().map { it.toImage() }

    override fun findOne(id: Int): Image? = Images.select { Images.id eq id }.firstOrNull()?.toImage()

    override fun save(value: Image): Image {
        val id = if (Images.select { Images.id eq value.id }.empty()) {
            Images.insert {
                it[name] = value.name
                it[extension] = value.extension.mimeType
                it[file] = value.file
                it[thumb] = value.thumb
            }[Images.id]
        } else {
            Images.update({ Images.id eq value.id }) {
                it[name] = value.name
            }
            value.id
        }
        return value.copy(id = id)
    }

    override fun remove(value: Image) {
        Images.deleteWhere { Images.id eq value.id }
    }

    override fun remove(id: Int) {
        Images.deleteWhere { Images.id eq id }
    }

}
