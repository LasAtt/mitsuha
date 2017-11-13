@file:Suppress("EXPERIMENTAL_FEATURE_WARNING", "UnsafeCastFromDynamic")

package common

import kotlin.coroutines.experimental.*
import kotlin.js.Promise

fun launch(block: suspend () -> Unit) {
    async(block).catch { exception -> throw exception }
}

suspend fun <T> Promise<T>.await(): T = suspendCoroutine { cont ->
    then({ cont.resume(it) }).catch { cont.resumeWithException(it) }
}

fun <T> async(block: suspend () -> T): Promise<T> = Promise { resolve, reject ->
    block.startCoroutine(object : Continuation<T> {
        override val context: CoroutineContext get() = EmptyCoroutineContext
        override fun resume(value: T) {
            resolve(value)
        }

        override fun resumeWithException(exception: Throwable) {
            reject(exception)
        }
    })
}
