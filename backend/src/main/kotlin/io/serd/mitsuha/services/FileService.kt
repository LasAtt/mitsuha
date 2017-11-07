package io.serd.mitsuha.services

import io.serd.mitsuha.domain.Image
import net.coobird.thumbnailator.Thumbnails
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.io.File
import java.time.Instant

@Service
class FileService(
    @Value("\${file.storage.dir}") storage: String
) {
    private final val dir: File = File(storage)

    init {
        dir.mkdirs()
    }

    private val originalSuffix = "orig"
    private val thumbSuffix = "thumb"
    private val small = "small"
    private val largeSuffix = "large"

    fun save(file: File, extension: String, id: Int, path: String) {
        val fileName = Instant.now().epochSecond

        val imgDir = getDirectory(path, id)
        imgDir.deleteRecursively()
        imgDir.mkdirs()

        val destination = File(imgDir, "$fileName.$originalSuffix.$extension")
        file.copyTo(destination)
        val thumb = File(imgDir, "$fileName.$thumbSuffix.$extension")
        val small = File(imgDir, "$fileName.$small.$extension")
        val large = File(imgDir, "$fileName.$largeSuffix.$extension")

        Thumbnails.of(destination)
            .size(200, 200)
            .toFile(thumb)

        Thumbnails.of(destination)
            .size(550, 550)
            .toFile(small)

        Thumbnails.of(destination)
            .size(800, 800)
            .toFile(large)
    }

    private fun getDirectory(path: String, id: Int)= File(dir, path).let {
        File(it, "$id")
    }

    fun loadOrig(image: Image) = load(image, originalSuffix)

    fun loadThumb(image: Image) = load(image, thumbSuffix)

    fun loadSmall(image: Image) = load(image, small)

    fun loadLarge(image: Image) = load(image, largeSuffix)

    private fun load(image: Image, suffix: String): File =
        getDirectory(image.path, image.id).listFiles().first { it.name.contains(suffix) }

    fun delete(path: String, id: Int) {
        getDirectory(path, id).delete()
    }
}
