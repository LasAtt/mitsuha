@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package components.images.view

import api.getImage
import api.imageSrc
import imports.RouteResultMatch
import common.launch
import domain.Image
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.div
import react.dom.img

@JsModule("src/components/images/view/View.css")
external val styles: ViewStyle

interface ViewStyle {
    val imageDiv: String
    val imgTag: String
}

interface ViewProps : RProps {
    var match: RouteResultMatch<Params>

    interface Params : RProps {
        var id: Int
    }
}

interface ViewState : RState {
    var id: Int
    var image: Image?
    var expanded: Boolean
    var size: String
}

class View(props: ViewProps) : RComponent<ViewProps, ViewState>(props) {

    override fun ViewState.init(props: ViewProps) {
        this.id = props.match.params.id
        this.expanded = false
        this.size = "large"
    }

    override fun componentDidMount() {
        launch {
            val image = getImage(state.id)
            setState {
                this.image = image
            }
        }
    }

    private fun onImageClick() {
        setState {
            this.expanded = !this.expanded
            this.size = if (this.size == "large") "orig" else "large"
        }
    }

    override fun RBuilder.render() {
        val image = state.image
        div(classes = styles.imageDiv) {
            if (image != null) {
                img(classes = styles.imgTag, src = imageSrc(image.id, state.size, image.extension)) {
                    attrs {
                        onClickFunction = { onImageClick() }
                    }
                }
            }
        }
    }
}
