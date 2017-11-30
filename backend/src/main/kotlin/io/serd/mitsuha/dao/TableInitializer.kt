package io.serd.mitsuha.dao

import org.jetbrains.exposed.sql.SchemaUtils
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Transactional
interface TableInitializer {
    fun createTables()
}

@Component
@Transactional
class TableInitializerImpl : TableInitializer {
    override fun createTables() = SchemaUtils.create(Images, Tags, ImageTags)
}
