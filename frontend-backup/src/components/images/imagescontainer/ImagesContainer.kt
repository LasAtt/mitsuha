@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package components.images.imagescontainer

import common.launch
import components.images.imagematrix.ImageMatrix
import domain.Image
import react.*
import reducers.*
import redux.Action
import redux.Dispatch
import kotlin.reflect.KClass

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

fun imagesContainer() = ConnectedComponent(
    mapStateToProps = { state: RootState ->
        images = state.images.images
    },
    mapDispatchToProps = { dispatch: Dispatch ->
        fetchImages = { fetchImages(dispatch) }
    },
    innerComponent = ImagesContainer::class
)

val fetchImages = { dispatch: (Action) -> Any ->
    dispatch(ImagesAction.Get)
    launch {
        try {
            dispatch(ImagesAction.GetSuccess(api.getImages()))
        } catch (t: dynamic) {
            dispatch(ImagesAction.GetFailure(t.response.data, t.message))
        }
    }
}


class ConnectedComponent<P: RProps, S: RState, C: RComponent<P, S>>(
    val innerComponent: KClass<C>,
    val mapStateToProps: P.(state: RootState) -> Unit,
    val mapDispatchToProps: P.(dispatch: Dispatch) -> Unit
) : RComponent<RProps, RState>() {

    private val store = Store

    private val subscription = Store.subscribe {
        this.setState { }
    }

    override fun componentWillUnmount() {
        subscription()
    }

    override fun RBuilder.render() {
        child(innerComponent) {
            attrs.mapStateToProps(store.state)
            attrs.mapDispatchToProps(store.dispatch)
        }
    }

}
