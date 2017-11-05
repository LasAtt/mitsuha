package components.navbar

import kotlinx.html.onClick
import react.*
import react.dom.*

sealed class MainView {
    object Home : MainView()
    object Images : MainView()
}

interface NavBarState : RState {
    var mainView: MainView
}

class NavBar : RComponent<RProps, NavBarState>() {

    override fun NavBarState.init() {
        mainView = MainView.Home
    }

    fun setMainView(mainView: MainView) {
        console.log(mainView)
        setState {
            this.mainView = mainView
        }
    }

    override fun RBuilder.render() {
        div {
            nav {
                attrs {
                    onClick = { setMainView(MainView.Home) }.asDynamic()
                }
                +"Home"
            }
            nav {
                attrs {
                    onClick = { setMainView(MainView.Images) }.asDynamic()
                }
                +"Images"
            }
        }
    }
}

fun RBuilder.navBar() = child(NavBar::class) {}
