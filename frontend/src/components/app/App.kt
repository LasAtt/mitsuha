package components.app

import common.hashRouter
import common.route
import common.switch
import components.images.Images
import components.logo.logo
import components.main.main
import components.navbar.navBar
import components.ticker.ticker
import components.view.View
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.code
import react.dom.div
import react.dom.h2
import react.dom.p


class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        hashRouter {
            div {
                div("appHeader") {
                    logo()
                    h2 {
                        +"Mitsuha Image Gallery"
                    }
                    navBar()
                }

                main {
                    switch {
                        route(path = "/", exact = true, component = Home::class)
                        route(path = "/images/:id", component = View::class)
                        route(path = "/images", exact = true, component = Images::class)
                    }
                }
            }
        }
    }
}

class Home : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        div {
            p("appIntro") {
                +"To get started, edit "
                code { +"components/app/App.kt" }
                +" and save to reload."
            }
            p("appTicker") {
                ticker()
            }
        }
    }
}

fun RBuilder.app() = child(App::class) {}
