package io.serd.mitsuha.controllers

import io.serd.mitsuha.domain.Tag
import io.serd.mitsuha.services.TagService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/tags")
class TagController(val tagService: TagService) {

    data class TagDTO(val name: String, val description: String = "")

    @PostMapping
    fun newTag(@RequestBody tagDTO: TagDTO): Tag {
        val (name, description) = tagDTO
        return tagService.save(name, description)
    }

    @GetMapping("/{id}")
    fun getTag(@RequestParam id: Int) = tagService.findOne(id)

    @GetMapping
    fun getAll() = tagService.findAll()

    @PostMapping("/{id}")
    fun updateTag(@RequestBody tag: Tag): Tag = tagService.update(tag)

    @DeleteMapping("/{id}")
    fun deleteTag(@RequestParam id: Int) = tagService.delete(id)
}
