package imports

import kotlin.js.Json
import kotlin.js.Promise

@JsModule("axios")
external object axios {
    fun <T> request(config: RequestConfig? = definedExternally): Promise<RequestResponse<T>>

    fun <T> get(url: String, config: RequestConfig? = definedExternally): Promise<RequestResponse<T>>

    fun <T> post(url: String, data: dynamic, config: RequestConfig? = definedExternally): Promise<RequestResponse<T>>

    fun <T> delete(url: String, config: RequestConfig? = definedExternally): Promise<RequestResponse<T>>

    fun <T> options(url: String, config: RequestConfig? = definedExternally): Promise<RequestResponse<T>>

    fun <T> put(url: String, data: dynamic, config: RequestConfig? = definedExternally): Promise<RequestResponse<T>>

    fun <T> patch(url: String, data: dynamic, config: RequestConfig? = definedExternally): Promise<RequestResponse<T>>
}

external interface RequestConfig {
    var url: String
    var method: String?
    var baseUrl: String
    var data: dynamic
    var headers: dynamic
    var responseType: String?
}

external interface RequestResponse<out T> {
    val data: T
    val status: Int
    val statusText: String
    val headers: Json
    val config: Json
    val request: Json
}
