package components.header.header

import components.header.logo.Logo
import components.header.navbar.NavBar
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.div
import react.dom.h2

@JsModule("src/components/header/header/Header.css")
external val styles: HeaderStyles

interface HeaderStyles {
    val appHeader: String
}

class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        div(styles.appHeader) {
            Logo()
            h2 {
                +"Mitsuha Image Gallery"
            }
            NavBar()
        }
    }
}

fun RBuilder.Header() = child(App::class) {}
