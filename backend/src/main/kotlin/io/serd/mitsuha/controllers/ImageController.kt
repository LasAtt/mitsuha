package io.serd.mitsuha.controllers

import io.serd.mitsuha.domain.mediaType
import io.serd.mitsuha.buffered_image.scaleTo64Bit
import io.serd.mitsuha.buffered_image.toGrayScale
import io.serd.mitsuha.domain.Image
import io.serd.mitsuha.services.ImageService
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.util.*
import javax.imageio.ImageIO


@RestController
@RequestMapping("/images")
class ImageController(
    val imageService: ImageService
) {

    @PostMapping
    fun uploadImage(@RequestParam file: MultipartFile) = imageService.saveImage(file)

    @GetMapping("/{id}")
    fun getImage(@PathVariable id: Int) = imageService.findOne(id)

    @GetMapping
    fun getImages() = imageService.findAll()

    @GetMapping("/orig/{id}")
    fun getOrigImage(@PathVariable id: Int): ResponseEntity<ByteArray> =
        imageService.loadOrig(id).let {
            ok().contentType(it.first.mediaType())
                .headers(fileHeaders(it.first))
                .body(it.second.readBytes())
        }

    @GetMapping("/thumb/{id}")
    fun getThumb(@PathVariable id: Int): ResponseEntity<ByteArray> =
        imageService.loadThumb(id).let {
            ok().contentType(it.first.mediaType())
                .headers(fileHeaders(it.first))
                .body(it.second.readBytes())
        }


    @GetMapping("/small/{id}")
    fun getSmall(@PathVariable id: Int): ResponseEntity<ByteArray> =
        imageService.loadSmall(id).let {
            ok().contentType(it.first.mediaType())
                .headers(fileHeaders(it.first))
                .body(it.second.readBytes())
        }

    @GetMapping("/large/{id}")
    fun getLarge(@PathVariable id: Int): ResponseEntity<ByteArray> =
        imageService.loadLarge(id).let {
            ok().contentType(it.first.mediaType())
                .headers(fileHeaders(it.first))
                .body(it.second.readBytes())

        }

    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id: Int) {
        imageService.deleteImage(id)
    }

    private fun fileHeaders(image: Image): HttpHeaders {
        val headers = HttpHeaders()
        headers.add("Content-disposition",
            "inline; filename=${image.name}${image.id}")
        return headers
    }


}
