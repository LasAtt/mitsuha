package io.serd.mitsuha.services

import com.sun.tools.internal.ws.wsdl.framework.NoSuchEntityException
import io.serd.mitsuha.buffered_image.computeHash
import io.serd.mitsuha.dao.ImageRepository
import io.serd.mitsuha.domain.Extension
import io.serd.mitsuha.domain.Image
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.imageio.ImageIO

interface ImageService {
    fun createTable()
    fun findOne(id: Int): Image?
    fun findAll(): List<Image>
    fun saveImage(file: MultipartFile): Image
    fun updateImage(image: Image): Image
    fun deleteImage(image: Image)
    fun deleteImage(id: Int)
    fun loadOrig(id: Int): Pair<Image, File>
    fun loadThumb(id: Int): Pair<Image, File>
    fun loadSmall(id: Int): Pair<Image, File>
    fun loadLarge(id: Int): Pair<Image, File>
}

@Service
@Transactional
class ImageServiceImpl(val repository: ImageRepository, val fileService: FileService): ImageService {

    override fun createTable() {
        repository.createTable()
    }

    override fun findOne(id: Int) = repository.findOne(id)

    override fun findAll() = repository.findAll()

    override fun saveImage(file: MultipartFile): Image {
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

    override fun updateImage(image: Image) = repository.save(image)

    override fun deleteImage(image: Image) {
        fileService.delete(image.path, image.id)
        repository.remove(image)
    }

    override fun deleteImage(id: Int) {
        val image = findOne(id) ?: return
        fileService.delete(image.path, image.id)
        repository.remove(image)
    }

    private fun findOneOrThrow(id: Int) = findOne(id) ?: throw NoSuchEntityException("No image with id $id exists!")

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
