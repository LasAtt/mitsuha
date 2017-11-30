package io.serd.mitsuha.services

import io.serd.mitsuha.buffered_image.computeHash
import io.serd.mitsuha.dao.TagRepository
import io.serd.mitsuha.domain.Tag
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.File
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import javax.imageio.ImageIO

interface TagService : CrudService<Tag, Int> {
    fun save(name: String, description: String): Tag
}

@Service
@Transactional
class TagServiceImpl(override val repository: TagRepository): TagService, AbstractCrudService<Tag, Int>() {
    override fun save(name: String, description: String): Tag {
        return repository.save(name, description)
    }
}
