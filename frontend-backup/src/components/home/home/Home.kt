package components.home.home

import components.home.ticker.Ticker
import react.RBuilder
import react.RComponent
import react.RProps
import react.RState
import react.dom.code
import react.dom.div
import react.dom.p

@JsModule("src/components/app/App.css")
external val styles: HomeStyles

interface HomeStyles {
    val appIntro: String
    val appTicker: String
}

class Home : RComponent<RProps, RState>() {


    override fun RBuilder.render() {
        div {
            p(classes = styles.appIntro) {
                +"To get started, edit "
                code { +"components/app/App.kt" }
                +" and save to reload."
            }
            p(classes = styles.appTicker) {
                Ticker()
            }
        }
    }
}
