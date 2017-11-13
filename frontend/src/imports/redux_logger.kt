@file:JsModule("redux-logger")

package imports

import kotlin.js.Json

//https://github.com/evgenyrodionov/redux-logger for options
external fun createLogger(options: Json = definedExternally): dynamic
