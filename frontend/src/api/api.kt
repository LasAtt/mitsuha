@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "UnsafeCastFromDynamic")

package api

import common.*
import domain.Image
import org.w3c.fetch.Request
import org.w3c.files.Blob
import kotlin.browser.window

suspend fun getImage(id: Int): Image? {
    return window.fetch(
        "http://localhost:8080/images/$id"
    ).then({ response ->
        response.body
    }).await()
}

suspend fun getImageFile(id: Int): Blob? {
    return window.fetch(
        Request("http://localhost:8080/images/image/$id")
    ).then({ response ->
        response.blob().asDynamic()
    }).await()
}

suspend fun getImages(): List<Image> {
    val images: Array<Image> = window.fetch(
        "http://localhost:8080/images/"
    ).then({ response ->
        response.json().asDynamic()
    }).await()
    return images.asList()
}
