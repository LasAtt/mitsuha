import components.app.*
import kotlinext.js.*
import react.dom.*
import kotlin.browser.*

fun main(args: Array<String>) {
    require("src/index.css")
    require("src/components/logo/Logo.css")
    require("src/components/app/App.css")
    require("src/components/navbar/NavBar.css")

    render(document.getElementById("root")) {
        app()
    }
}
