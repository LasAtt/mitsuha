package components.app

import react.*
import react.dom.*
import components.logo.*
import components.navbar.navBar
import components.view.view


class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        div {
            div("appHeader") {
                logo()
                h2 {
                    +"Welcome to React with Kotlin"
                }
            }
            p("appIntro") {
                +"To get started, edit "
                code { +"components/app/App.kt" }
                +" and save to reload."
            }
            p("appTicker") {
//            ticker()
            }
            view(1)
            navBar()
        }
    }
}

fun RBuilder.app() = child(App::class) {}
