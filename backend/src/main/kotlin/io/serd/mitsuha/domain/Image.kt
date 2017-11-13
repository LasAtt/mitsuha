package io.serd.mitsuha.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.MediaType

data class Image(
    val id: Int,
    val name: String? = null,
    val path: String,
    val extension: Extension,
    @JsonIgnore val hash: Long
)

enum class Extension(val mimeType: String) {
    @JsonProperty("png") PNG("image/png"),
    @JsonProperty("jpg") JPEG("image/jpeg"),
    @JsonProperty("gif") GIF("image/gif");

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
