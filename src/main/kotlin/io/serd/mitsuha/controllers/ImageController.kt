package io.serd.mitsuha.controllers

import io.serd.mitsuha.dao.Image
import io.serd.mitsuha.dao.ImageRepository
import io.serd.mitsuha.services.StorageService
import io.serd.mitsuha.util.readBytes
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File


@RestController
@RequestMapping("/images")
class ImageController(
    val imageRepository: ImageRepository,
    val storageService: StorageService
) {

    @PostMapping
    fun uploadImage(@RequestParam file: MultipartFile): Image {
        val image = storageService.save(file).let {
            Image(file = it.absolutePath, extension = it.extension)
        }
        return imageRepository.save(image)
    }

    @GetMapping("/{id}")
    fun getImage(@PathVariable id: Int) = imageRepository.findOne(id)

    @GetMapping
    fun getImages() = imageRepository.findAll()

    @RequestMapping(value = "/image/{id}", method = arrayOf(RequestMethod.GET))
    fun getImageFile(@PathVariable id: Int): ResponseEntity<ByteArray> {
        val image = imageRepository.findOne(id)
            ?: throw Exception("No such image with id $id!")
        return ok()
            .contentType(MediaType.IMAGE_PNG)
            .body(storageService.load(image.file).readBytes())
    }

}
