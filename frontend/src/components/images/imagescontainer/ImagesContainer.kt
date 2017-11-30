@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package components.images.imagescontainer

import common.launch
import components.images.imagematrix.ImageMatrix
import domain.Image
import react.*
import reducers.*
import redux.Action
import redux.Dispatch
import redux.connect

interface ImagesProps : RProps {
    var images: Set<Image>
    var fetchImages: () -> Unit
}

interface ImagesState : RState

class ImagesContainer(props: ImagesProps) : RComponent<ImagesProps, ImagesState>(props) {

    override fun componentDidMount() {
        props.fetchImages()
    }

    override fun componentWillUnmount() {
    }

    override fun RBuilder.render() {
        ImageMatrix {
            attrs.images = props.images
        }
    }
}

val fetchImages = { dispatch: (Action) -> Any ->
    launch {
        try {
            dispatch(ImagesAction.AddAll(api.getImages()))
        } catch (t: dynamic) {
            dispatch(ImagesAction.Error(t.response.data, t.message))
        }
    }
}

fun imagesContainer() = connect(
    connectedComponent = ImagesContainer::class,
    mapStateToProps = { state: RootState ->
        images = state.images.images
    },
    mapDispatchToProps = { dispatch: Dispatch ->
        fetchImages = { fetchImages(dispatch) }
    }
)
