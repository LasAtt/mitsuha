package components.images.imagematrix

import api.imageSrc
import common.RouteLink
import domain.Image
import react.*
import react.dom.div
import react.dom.img

@JsModule("src/components/images/imagematrix/ImageMatrix.css")
external val styles: ImagesStyles

interface ImagesStyles {
    val matrix: String
    val image: String
}

interface ImageMatrixProps : RProps {
    var images: Set<Image>
}

class ImageMatrix(props: ImageMatrixProps) : RComponent<ImageMatrixProps, RState>(props) {

    override fun RBuilder.render() {
        div(classes = styles.matrix) {
            for(image in props.images) {
                thumbnail(image)
            }
        }
    }

    private fun RBuilder.thumbnail(image: Image) {
        div(classes = styles.image) {
            key = "${image.id}"
            RouteLink("/images/${image.id}") {
                img(src = imageSrc(image.id, "thumb", image.extension)) {
                    key = "${image.id}"
                }
            }
        }
    }
}

fun RBuilder.ImageMatrix(handler: RHandler<ImageMatrixProps>) = child(ImageMatrix::class) {
    handler()
}
