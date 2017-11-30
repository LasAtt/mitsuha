import components.app.App
import kotlinext.js.invoke
import kotlinext.js.require
import react.dom.render
import kotlin.browser.document

fun main(args: Array<String>) {
    require("src/config/variables.css")
    require("src/index.css")

    render(document.getElementById("root")) {
        App()
    }
}
