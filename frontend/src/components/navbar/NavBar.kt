package components.navbar

import kotlinx.html.js.onClickFunction
import react.*
import react.dom.*

sealed class MainView {
    object Home : MainView()
    object Images : MainView()
}

interface NavBarProps : RProps {
    var activeView: MainView
    var changeView: (MainView) -> Unit
}

class NavBar : RComponent<NavBarProps, RState>() {

    override fun RBuilder.render() {
        nav(classes = "navBar") {
            navItem(label = "Home") { props.changeView(MainView.Home) }
            navItem(label = "Images") { props.changeView(MainView.Images) }
        }
    }
}

fun RBuilder.navBar(activeView: MainView, changeView: (MainView) -> Unit) = child(NavBar::class) {
    attrs {
        this.activeView = activeView
        this.changeView = changeView
    }
}

fun RBuilder.navItem(label: String, onClick: () -> Unit) {
    a(classes = "navItem") {
        attrs {
            onClickFunction = { onClick() }
        }
        +label
    }
}
