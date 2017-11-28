package components.images.image

import api.imageSrc
import domain.Image
import kotlinx.html.js.onClickFunction
import react.*
import react.dom.div
import react.dom.img

@JsModule("src/components/images/image/Image.css")
external val styles: ViewStyle

interface ViewStyle {
    val imageDiv: String
    val imgTag: String
}

interface ImageProps : RProps {
    var image: Image
}

interface ImageState : RState {
    var size: String
}

class ImageComponent(props: ImageProps) : RComponent<ImageProps, ImageState>(props) {

    override fun ImageState.init(props: ImageProps) {
        size = "large"
    }

    private fun onImageClick() {
        setState {
            this.size = if (this.size == "large") "orig" else "large"
        }
    }

    override fun RBuilder.render() {
        div(classes = styles.imageDiv) {
            img(classes = styles.imgTag, src = imageSrc(props.image.id, state.size, props.image.extension)) {
                attrs {
                    onClickFunction = { onImageClick() }
                }
            }
        }
    }
}

fun RBuilder.Image(image: Image) = child(ImageComponent::class) {
    attrs.image = image
}
