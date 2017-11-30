package common

import kotlin.js.Json
import kotlin.js.Promise

@JsModule("axios")
external object axios {
    fun <T> invoke(config: AxiosConfigSettings): Promise<AxiosResponse<T>>

    fun <T> request(config: AxiosConfigSettings? = definedExternally): Promise<AxiosResponse<T>>

    fun <T> get(url: String, config: AxiosConfigSettings? = definedExternally): Promise<AxiosResponse<T>>

    fun <T> post(url: String, data: dynamic, config: AxiosConfigSettings? = definedExternally): Promise<AxiosResponse<T>>

    fun <T> delete(url: String, config: AxiosConfigSettings? = definedExternally): Promise<AxiosResponse<T>>

    fun <T> options(url: String, config: AxiosConfigSettings? = definedExternally): Promise<AxiosResponse<T>>

    fun <T> put(url: String, data: dynamic, config: AxiosConfigSettings? = definedExternally): Promise<AxiosResponse<T>>

    fun <T> patch(url: String, data: dynamic, config: AxiosConfigSettings? = definedExternally): Promise<AxiosResponse<T>>
}

external interface AxiosConfigSettings {
    var url: String
    var method: String
    var baseUrl: String
    var timeout: Number
    var data: dynamic
    var transferRequest: dynamic
    var transferResponse: dynamic
    var headers: dynamic
    var params: dynamic
    var withCredentials: Boolean
    var adapter: dynamic
    var auth: dynamic
    var responseType: String
    var xsrfCookieName: String
    var xsrfHeaderName: String
    var onUploadProgress: dynamic
    var onDownloadProgress: dynamic
    var maxContentLength: Number
    var validateStatus: (Number) -> Boolean
    var maxRedirects: Number
    var httpAgent: dynamic
    var httpsAgent: dynamic
    var proxy: dynamic
    var cancelToken: dynamic
}

external interface AxiosResponse<T> {
    val data: T
    val status: Number
    val statusText: String
    val headers: dynamic
    val config: AxiosConfigSettings
}
