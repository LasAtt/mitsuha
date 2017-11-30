package components.header.navbar

import common.NavLink
import react.*
import react.dom.*

@JsModule("src/components/header/navbar/NavBar.css")
external val styles: NavStyles

interface NavStyles {
    val navItem: String
    val navBar: String
    val activeItem: String
}

class NavBar : RComponent<RProps, RState>() {

    fun RBuilder.MyLink(to: String, children: RBuilder.() -> Unit) =
        NavLink(to = to, classes = styles.navItem, activeClasses = styles.activeItem, exact = true) {
            children()
        }

    override fun RBuilder.render() {
        nav(classes = styles.navBar) {
            MyLink("/") { +"Home" }
            MyLink("/images") { +"Images" }
            MyLink("/upload") { +"Upload" }
        }
    }
}

fun RBuilder.NavBar() = child(NavBar::class) { }
