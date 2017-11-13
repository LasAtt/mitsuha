@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "UnsafeCastFromDynamic")

package api

import common.await
import imports.axios
import domain.Image
import org.w3c.files.File
import org.w3c.xhr.FormData

fun imageSrc(id: Int, size: String, extension: String) =
    "/api/images/resource/$id-$size.$extension"

suspend fun getImage(id: Int): Image = axios
    .get<Image>("/api/images/$id")
    .await()
    .data

suspend fun getImages(): List<Image> = axios
    .get<Array<Image>>("/api/images")
    .await()
    .data
    .asList()

suspend fun sendImage(data: File): Image {
    val formData = FormData()
    formData.append("file", data, data.name)
    return axios.post<Image>("/api/images/", formData)
        .await()
        .data
}
