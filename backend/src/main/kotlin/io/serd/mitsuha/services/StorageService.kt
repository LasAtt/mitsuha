package io.serd.mitsuha.services

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.Instant

@Service
class StorageService(
    @Value("\${file.storage.dir}") storage: String
) {
    private final val dir: File = File(storage)

    init {
        dir.mkdirs()
    }

    fun save(file: MultipartFile): File {
        val fileName = Instant.now().epochSecond
        val extension = file.contentType!!.split("/").last()
        val destination = File(dir, "$fileName.$extension")
        file.transferTo(destination)
        return destination
    }

    fun load(filename: String): File {
        return File(filename)
    }

    fun delete(filename: String) {
        val file = File(filename)
        file.delete()
    }
}
