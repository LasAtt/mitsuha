import kotlinext.js.*
import react.dom.*
import redux.ReduxStore
import kotlin.browser.*

import components.app.*
import reducers.*

fun main(args: Array<String>) {
    require("src/config/variables.css")
    require("src/index.css")
    require("src/components/logo/Logo.css")
    require("src/components/app/App.css")
    require("src/components/images/Images.css")
    require("src/components/navbar/NavBar.css")
    require("src/components/main/Main.css")

    val store = ReduxStore(::rootReducer)

    render(document.getElementById("root")) {
        app()
    }
}
