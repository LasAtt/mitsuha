package io.serd.mitsuha.domain

data class Tag(
    val id: Int,
    val name: String,
    val description: String = ""
)
