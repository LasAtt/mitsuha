package components.navbar

import common.routeLink
import react.*
import react.dom.*

class NavBar : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        nav(classes = "navBar") {
            routeLink(to = "/", classes = "navItem") { +"Home" }
            routeLink(to = "/images", classes = "navItem") { +"Images" }
        }
    }
}

fun RBuilder.navBar() = child(NavBar::class) { }
