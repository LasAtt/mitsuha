package io.serd.mitsuha.util

import org.springframework.http.HttpHeaders

fun HttpHeaders.addHeader(headerName: String, headerValue: String): HttpHeaders {
    this.add(headerName, headerValue)
    return this
}
