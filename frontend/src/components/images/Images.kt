package components.images

import api.getThumbnail
import common.launch
import common.routeLink
import domain.Image
import org.w3c.dom.url.URL
import react.*
import react.dom.div
import react.dom.img

interface ImagesState : RState {
    var images: List<Image>
    var thumbnails: Map<Int, String>
}

class Images : RComponent<RProps, ImagesState>() {

    override fun ImagesState.init() {
        images = listOf()
        thumbnails = mapOf()
    }

    @Suppress("EXPERIMENTAL_FEATURE_WARNING")
    override fun componentDidMount() {
        launch {
            val images = api.getImages()
            val thumbnails = images.mapNotNull { image ->
                val blob = getThumbnail(image.id) ?: return@mapNotNull null
                image.id to URL.createObjectURL(blob)
            }.toMap()
            setState {
                this.images = images
                this.thumbnails = thumbnails
            }
        }
    }

    private fun RBuilder.thumbnail(image: Image) {
        div(classes = "imagesImage") {
            key = "${image.id}"
            routeLink("/images/${image.id}") {
                img(src = state.thumbnails[image.id]) {
                    key = "${image.id}"
                }
            }
        }
    }

    override fun RBuilder.render() {
        div(classes = "images") {
            for(image in state.images) {
                thumbnail(image)
            }
        }
    }
}

fun RBuilder.images() = child(Images::class) { }
