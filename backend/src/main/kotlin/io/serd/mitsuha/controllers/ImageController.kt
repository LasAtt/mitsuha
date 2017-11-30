package io.serd.mitsuha.controllers

import io.serd.mitsuha.domain.Image
import io.serd.mitsuha.domain.Tag
import io.serd.mitsuha.domain.mediaType
import io.serd.mitsuha.services.ImageService
import io.serd.mitsuha.util.addHeader
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.io.File


@RestController
@RequestMapping("/images")
class ImageController(
    val imageService: ImageService
) {

    @PostMapping
    fun uploadImage(@RequestParam file: MultipartFile) = imageService.save(file)

    @GetMapping("/{id}")
    fun getImage(@PathVariable id: Int) = imageService.findOne(id)

    @GetMapping
    fun getImages() = imageService.findAll()

    @PutMapping("/{id}/tags")
    fun addTag(@PathVariable id: Int, @RequestBody tag: Tag) = imageService.addTag(id, tag)

    @DeleteMapping("/{id}")
    fun deleteImage(@PathVariable id: Int) {
        imageService.delete(id)
    }

    @GetMapping("/resource/{id}-orig.{extension}")
    fun getOrigImage(@PathVariable id: Int) =
        imageService.loadOrig(id).let {
            fileResponse(it)
        }

    @GetMapping("/resource/{id}-thumb.{extension}")
    fun getThumb(@PathVariable id: Int) =
        imageService.loadThumb(id).let {
            fileResponse(it)
        }

    @GetMapping("/resource/{id}-small.{extension}")
    fun getSmall(@PathVariable id: Int)  =
        imageService.loadSmall(id).let {
            fileResponse(it)
        }

    @GetMapping("/resource/{id}-large.{extension}")
    fun getLarge(@PathVariable id: Int) =
        imageService.loadLarge(id).let {
            fileResponse(it)
        }

    private fun fileResponse(it: Pair<Image, File>): ResponseEntity<ByteArray> = ok()
        .contentType(it.first.mediaType())
        .headers(fileHeaders(it.first))
        .body(it.second.readBytes())

    private fun fileHeaders(image: Image): HttpHeaders {
        return HttpHeaders()
            .addHeader("Cache-Control", "max-axe=600")
    }

}
