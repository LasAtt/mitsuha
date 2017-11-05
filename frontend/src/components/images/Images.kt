package components.images

import api.getImageFile
import common.launch
import domain.Image
import org.w3c.dom.url.URL
import react.*
import react.dom.div
import react.dom.img

interface ImagesState : RState {
    var images: List<Image>
    var imageFiles: Map<Int, String>
}

class Images : RComponent<RProps, ImagesState>() {

    override fun ImagesState.init() {
        images = listOf()
        imageFiles = mapOf()
    }

    @Suppress("EXPERIMENTAL_FEATURE_WARNING")
    override fun componentDidMount() {
        launch {
            val images = api.getImages()
            val imageFiles = images.mapNotNull { image ->
                val blob = getImageFile(image.id) ?: return@mapNotNull null
                image.id to URL.createObjectURL(blob)
            }.toMap()
            setState {
                this.images = images
                this.imageFiles = imageFiles
            }
        }
    }

    override fun RBuilder.render() {
        div {
            state.images.map { image ->
                img(src = state.imageFiles[image.id]) {
                    key = "${image.id}"
                }
            }
        }
    }
}

fun RBuilder.images() = child(Images::class) { }
