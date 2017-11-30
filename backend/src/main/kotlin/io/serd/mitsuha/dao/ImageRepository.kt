package io.serd.mitsuha.dao

import io.serd.mitsuha.domain.Extension
import io.serd.mitsuha.domain.Image
import io.serd.mitsuha.domain.Tag
import io.serd.mitsuha.exceptions.NoSuchEntityException
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository

object Images : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val name = varchar("name", 128).nullable()
    val extension = varchar("extension", 32)
    val path = varchar("path", 32)
    val hash = long("hash")
}

object ImageTags : Table() {
    val id = integer("id").autoIncrement().primaryKey()
    val imageId = integer("imageId") references Images.id
    val tagId = integer("tagId") references Tags.id
}

interface ImageRepository : CrudRepository<Image, Int> {
    fun save(name: String, extension: Extension, path: String, hash: Long): Image
    fun addTag(id: Int, tag: Tag): Set<Tag>
    fun getTags(id: Int): Set<Tag>
    fun removeTag(id: Int, tagId: Int): Set<Tag>
}

@Repository
class ImageRepositoryImpl : ImageRepository {
    private fun ResultRow.toImage() = Image(
        id = this[Images.id],
        name = this[Images.name],
        extension = Extension.fromMimeType(this[Images.extension]),
        path = this[Images.path],
        hash = this[Images.hash]
    )

    override fun findAll(): List<Image> {
        return Images
            .selectAll()
            .map { it.toImage() }
            .map { it.copy(tags = getTags(it.id)) }
    }

    override fun findOne(id: Int): Image? {
        return Images
            .select { Images.id eq id }
            .firstOrNull()
            ?.toImage()
            ?.copy(tags = getTags(id))
    }

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
            it[Images.name] = value.name
        }
        return findOne(value.id)
            ?: throw NoSuchEntityException("Entity was not found after saving it to database!")
    }

    override fun addTag(id: Int, tag: Tag): Set<Tag> {
        transaction {
            ImageTags.insert {
                it[ImageTags.imageId] = id
                it[ImageTags.tagId] = tag.id
            }
        }
        return getTags(id)
    }

    override fun getTags(id: Int): Set<Tag> {
        return (ImageTags innerJoin Tags)
            .slice(ImageTags.imageId, Tags.id, Tags.name, Tags.description)
            .select { ImageTags.id eq id }
            .map { Tag(it[Tags.id], it[Tags.name], it[Tags.description]) }
            .toSet()
    }

    override fun removeTag(id: Int, tagId: Int): Set<Tag> {
        ImageTags.deleteWhere { (ImageTags.imageId eq id) and (ImageTags.tagId eq tagId) }
        return getTags(id)
    }

    override fun remove(value: Image) {
        Images.deleteWhere { Images.id eq value.id }
        ImageTags.deleteWhere { ImageTags.imageId eq value.id }
    }

    override fun remove(id: Int) {
        Images.deleteWhere { Images.id eq id }
    }

}
