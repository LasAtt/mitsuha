package components.header.logo

import react.*
import react.dom.*
import kotlinext.js.*
import kotlinx.html.style

@JsModule("src/components/header/logo/react.svg")
external val reactLogo: dynamic
@JsModule("src/components/header/logo/kotlin.svg")
external val kotlinLogo: dynamic
@JsModule("src/components/header/logo/Logo.css")
external val styles: dynamic

@Suppress("UnsafeCastFromDynamic")
fun RBuilder.Logo(height: Int = 100) {
    div(styles.logo) {
        attrs.style = js {
            this.height = height
        }
        img(alt = "React logo", src = reactLogo, classes = styles.logoReact) {}
        img(alt = "Kotlin logo", src = kotlinLogo, classes = styles.logoKotlin) {}
    }
}
