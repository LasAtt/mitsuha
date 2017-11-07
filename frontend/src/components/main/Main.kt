package components.main

import react.dom.div
import react.*

interface MainProps : RProps {
    var children: dynamic
}

class Main : RComponent<MainProps, RState>() {

    override fun RBuilder.render() {
        div(classes = "main") {
            children()
        }
    }

}

fun RBuilder.main(children: RBuilder.() -> Unit) = child(Main::class) {
    attrs.children = buildElements { children() }
}

