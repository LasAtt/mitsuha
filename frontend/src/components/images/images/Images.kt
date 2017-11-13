package components.images.images

import common.launch
import common.RouteLink
import domain.Image
import react.*
import react.dom.div
import react.dom.img

@JsModule("src/components/images/images/Images.css")
external val styles: ImagesStyles

interface ImagesStyles {
    val matrix: String
    val image: String
}

interface ImagesState : RState {
    var images: List<Image>
}

class Images : RComponent<RProps, ImagesState>() {

    override fun ImagesState.init() {
        images = listOf()
    }

    @Suppress("EXPERIMENTAL_FEATURE_WARNING")
    override fun componentDidMount() {
        launch {
            val images = api.getImages()
            setState {
                this.images = images
            }
        }
    }

    private fun RBuilder.thumbnail(image: Image) {
        div(classes = styles.image) {
            key = "${image.id}"
            RouteLink("/images/${image.id}") {
                img(src = "/api/images/resource/${image.id}-thumb.${image.extension}") {
                    key = "${image.id}"
                }
            }
        }
    }

    override fun RBuilder.render() {
        div(classes = styles.matrix) {
            for(image in state.images) {
                thumbnail(image)
            }
        }
    }
}
