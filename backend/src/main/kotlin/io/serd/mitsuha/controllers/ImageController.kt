package io.serd.mitsuha.controllers

import io.serd.mitsuha.dao.ImageRepository
import io.serd.mitsuha.domain.Extension
import io.serd.mitsuha.domain.Image
import io.serd.mitsuha.domain.mediaType
import io.serd.mitsuha.exceptions.NoSuchEntityException
import io.serd.mitsuha.services.StorageService
import io.serd.mitsuha.util.readBytes
import kotlinx.coroutines.experimental.launch
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile


@RestController
@RequestMapping("/images")
class ImageController(
    val imageRepository: ImageRepository,
    val storageService: StorageService
) {

    @PostMapping
    fun uploadImage(@RequestParam file: MultipartFile): Image {
        val image = storageService.save(file).let {
            Image(
                file = it.absolutePath,
                extension = Extension.fromMimeType(file.contentType!!)
            )
        }
        launch {  }
        return imageRepository.save(image)
    }

    @GetMapping("/{id}")
    fun getImage(@PathVariable id: Int) = imageRepository.findOne(id)

    @GetMapping
    fun getImages() = imageRepository.findAll()

    @RequestMapping("/image/{id}", method = arrayOf(RequestMethod.GET))
    fun getImageFile(@PathVariable id: Int): ResponseEntity<ByteArray> {
        val image = imageRepository.findOne(id)
            ?: throw Exception("No such image with id $id!")
        return ok()
            .contentType(image.mediaType())
            .body(storageService.load(image.file).readBytes())
    }

    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id: Int) {
        val image = imageRepository.findOne(id)
            ?: throw NoSuchEntityException("No such image with id $id.")
        storageService.delete(image.file)
    }

}
