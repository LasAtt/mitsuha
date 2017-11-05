import kotlinext.js.*
import react.dom.*
import redux.ReduxStore
import kotlin.browser.*

import components.app.*
import reducers.*

fun main(args: Array<String>) {
    require("src/variables/variables.css")
    require("src/index.css")
    require("src/components/logo/Logo.css")
    require("src/components/app/App.css")
    require("src/components/navbar/NavBar.css")

    val store = ReduxStore(::rootReducer)

    render(document.getElementById("root")) {
        app()
    }
}
