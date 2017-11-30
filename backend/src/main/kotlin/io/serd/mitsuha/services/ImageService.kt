package io.serd.mitsuha.services

import io.serd.mitsuha.buffered_image.computeHash
import io.serd.mitsuha.dao.ImageRepository
import io.serd.mitsuha.domain.Extension
import io.serd.mitsuha.domain.Image
import io.serd.mitsuha.domain.Tag
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.imageio.ImageIO

interface ImageService : CrudService<Image, Int> {
    fun save(file: MultipartFile): Image

    fun addTag(id: Int, tag: Tag): Set<Tag>
    fun removeTag(id: Int, tagId: Int): Set<Tag>
    fun getTags(id: Int): Set<Tag>

    fun loadOrig(id: Int): Pair<Image, File>
    fun loadThumb(id: Int): Pair<Image, File>
    fun loadSmall(id: Int): Pair<Image, File>
    fun loadLarge(id: Int): Pair<Image, File>
}

@Service
@Transactional
class ImageServiceImpl(
    override val repository: ImageRepository,
    val fileService: FileService
): ImageService, AbstractCrudService<Image, Int>() {
    override fun save(file: MultipartFile): Image {
        val extension = Extension.fromMimeType(file.contentType!!)
        val suffix = extension.mimeType.split("/").last()
        val path = getPath()
        val tmpFile = file.transferToTmpFile()
        val hash = tmpFile.hash()
        val image = repository.save("", extension, path, hash)
        fileService.save(tmpFile, suffix, image.id, path)
        tmpFile.delete()
        return image
    }

    override fun delete(value: Image) {
        fileService.delete(value.path, value.id)
        repository.remove(value)
    }

    override fun delete(id: Int) {
        val image = findOne(id) ?: return
        fileService.delete(image.path, image.id)
        repository.remove(image)
    }

    override fun addTag(id: Int, tag: Tag): Set<Tag> = repository.addTag(id, tag)

    override fun removeTag(id: Int, tagId: Int): Set<Tag> = repository.removeTag(id, tagId)

    override fun getTags(id: Int): Set<Tag> = repository.getTags(id)

    override fun loadOrig(id: Int) = findOneOrThrow(id).let { it to fileService.loadOrig(it) }

    override fun loadThumb(id: Int) = findOneOrThrow(id).let { it to fileService.loadThumb(it) }

    override fun loadSmall(id: Int) = findOneOrThrow(id).let { it to fileService.loadSmall(it) }

    override fun loadLarge(id: Int) = findOneOrThrow(id).let { it to fileService.loadLarge(it) }
}

private fun getPath() = ZonedDateTime.now()
    .format(DateTimeFormatter.ofPattern("yyyy MM dd"))
    .replace(" ", File.separator)

private fun MultipartFile.transferToTmpFile(): File {
    val tmpFile = File("/tmp/mitsuha/tmp${UUID.randomUUID()}")
    this.transferTo(tmpFile)
    return tmpFile
}

private fun File.hash(): Long = computeHash(ImageIO.read(this))
