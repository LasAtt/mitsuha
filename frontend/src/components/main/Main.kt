package components.main

import kotlinext.js.jsObject
import react.dom.div
import react.*

@JsModule("src/components/main/Main.css")
external val styles: MainStyles

interface MainStyles {
    val main: String
}

class Main : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        div(classes = styles.main) {
            children()
        }
    }

}

fun RBuilder.Main(children: RHandler<RProps>) = child(Main::class) {
    children()
}

