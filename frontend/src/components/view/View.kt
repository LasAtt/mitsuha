@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package components.view

import api.getImage
import api.getImageFile
import common.launch
import domain.Image
import org.w3c.dom.url.URL
import react.*
import react.dom.div
import react.dom.img

interface ViewProps : RProps {
    var match: dynamic
}

interface ViewState : RState {
    var image: Image?
    var imageSrc: String?
}

class View(props: ViewProps) : RComponent<ViewProps, ViewState>(props) {

    override fun componentWillMount() {
        val id: Int = (props.match.params.id as String).toInt()
        launch {
            val image = getImage(id) ?: throw Throwable("Image with id $id doesn't exist")
            val imgSrc = getImageFile(id) ?: throw Throwable("Image with id $id doesn't exist")
            setState {
                this.image = image
                this.imageSrc = URL.createObjectURL(imgSrc)
            }
        }
    }

    override fun RBuilder.render() {
        div {
            img(alt = "loading", src = state.imageSrc) {  }
        }
    }
}
