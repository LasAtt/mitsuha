@file:JsModule("history")
package common

external interface history {
    var length: Int
    var location: location
    var action: String

    fun push(path: String, state: dynamic = definedExternally)
    fun replace(path: String, state: dynamic = definedExternally)
    fun go(n: Int)
    fun goBack()
    fun goForward()
    fun canGo(n: Int)
}

external interface location {
    var pathname: String
    var search: String
    var hash: String
    var state: dynamic
    var key: String
}
