import components.app.App
import domain.Image
import kotlinext.js.invoke
import kotlinext.js.require
import react.dom.render
import reducers.ImagesAction
import kotlin.browser.document

fun main(args: Array<String>) {
    require("src/config/variables.css")
    require("src/index.css")

    val store = store()
    store.dispatch(ImagesAction.GetSuccess(setOf(Image(1, "aadadw", ".png"))))
    render(document.getElementById("root")) {
        App()
    }
}
