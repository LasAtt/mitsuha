package io.serd.mitsuha.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import org.springframework.http.MediaType

data class Image(
    val id: Int,
    val name: String? = null,
    @JsonIgnore val extension: Extension,
    @JsonIgnore val path: String,
    @JsonIgnore val hash: Long
)

enum class Extension(val mimeType: String) {
    PNG("image/png"),
    JPEG("image/jpeg"),
    GIF("image/gif");

    companion object {
        fun fromMimeType(mimeType: String) = when(mimeType) {
            PNG.mimeType -> PNG
            JPEG.mimeType -> JPEG
            GIF.mimeType -> GIF
            else -> throw IllegalArgumentException("No such method")
        }
    }
}

fun Image.mediaType() = extension.mediaType()

fun Extension.mediaType(): MediaType = when(this) {
    Extension.PNG -> MediaType.IMAGE_PNG
    Extension.GIF -> MediaType.IMAGE_GIF
    Extension.JPEG -> MediaType.IMAGE_JPEG
}
