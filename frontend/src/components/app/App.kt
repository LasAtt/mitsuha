package components.app

import components.images.images
import react.*
import react.dom.*
import components.logo.*
import components.navbar.MainView
import components.navbar.navBar
import components.ticker.ticker
import components.view.view

interface AppState : RState {
    var view: MainView
}

class App : RComponent<RProps, AppState>() {

    override fun AppState.init() {
        view = MainView.Home
    }

    fun changeView(view: MainView) = setState {
        this.view = view
    }

    override fun RBuilder.render() {
        div {
            div("appHeader") {
                logo()
                h2 {
                    +"Mitsuha Image Gallery"
                }
                navBar(
                    activeView = state.view,
                    changeView = { view -> changeView(view) }
                )
            }
            when (state.view) {
                MainView.Home -> home()
                MainView.Images -> images()
            }
        }
    }
}

fun RBuilder.home() {
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

fun RBuilder.app() = child(App::class) {}
