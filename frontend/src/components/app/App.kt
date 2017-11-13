package components.app

import common.Route
import common.Router
import common.Switch
import components.header.header.Header
import components.home.home.Home
import components.images.images.Images
import components.images.view.View
import components.main.Main
import components.upload.Upload
import react.*
import react.dom.div

class App : RComponent<RProps, RState>() {

    override fun RBuilder.render() {
        Router {
            div {
                Header()
                Main {
                    Switch {
                        Route(path = "/", exact = true, component = Home::class)
                        Route(path = "/images/:id", component = View::class)
                        Route(path = "/images", exact = true, component = Images::class)
                        Route(path = "/upload", exact = true, component = Upload::class)
                    }
                }
            }
        }
    }
}

fun RBuilder.App() = child(App::class) {}
