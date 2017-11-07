@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "UnsafeCastFromDynamic")

package api

import common.await
import domain.Image
import org.w3c.fetch.RequestCredentials
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import org.w3c.files.Blob
import kotlin.browser.window
import kotlin.js.json

private val baseUrl = "http://localhost:8080"

suspend fun getImage(id: Int): Image? = getJson<Image>("/images/$id", null) { it }

suspend fun getImageFile(id: Int) = getBlob("/images/orig/$id", null)

suspend fun getThumbnail(id: Int) = getBlob("/images/thumb/$id", null)

suspend fun getImages(): List<Image> = getJson("/images", null) {
    val array = it as Array<Image>
    array.asList()
}


suspend fun <T> getJson(url: String, body: dynamic, parse: (dynamic) -> T) =
    requestJson("GET", url, body, parse)

suspend fun <T> postJson(url: String, body: dynamic, parse: (dynamic) -> T) =
    requestJson("POST", url, body, parse)

suspend fun <T> requestJson(method: String, url: String, body: dynamic, parse: (dynamic) -> T): T {
    val response = request(method, url, body)
    return parse(response.json().await())
}

suspend fun getBlob(url: String, body: dynamic) = requestBlob("GET", url, body)

suspend fun uploadBlob(url: String, body: dynamic) = requestBlob("POST", url, body)

suspend fun requestBlob(method: String, url: String, body: dynamic): Blob? {
    val response = request(method, url, body)
    return response.blob().await()
}

suspend fun request(method: String, url: String, body: dynamic): Response {
    return window.fetch(baseUrl + url, object : RequestInit {
        override var method: String? = method
        override var body: dynamic = body
        override var credentials: RequestCredentials? = "same-origin".asDynamic()
        override var headers: dynamic = json("Accept" to "application/json")
    }).await()
}
