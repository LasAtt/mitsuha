@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package components.images.viewcontainer

import api.getImage
import common.RouteResultMatch
import common.launch
import components.images.image.Image
import domain.Image
import react.*
import reducers.RootState
import redux.connect

interface RouteParams : RProps {
    var id: String
}

interface ViewProps : RProps {
    var images: Set<Image>

    var match: RouteResultMatch<RouteParams>
}

interface ViewState : RState {
    var id: Int
    var image: Image?
}

class ViewContainer(props: ViewProps) : RComponent<ViewProps, ViewState>(props) {

    override fun ViewState.init(props: ViewProps) {
        this.id = props.match.params.id.toInt()
    }

    override fun componentDidMount() {
        launch {
            val image = getImage(state.id)
            setState {
                this.image = image
            }
        }
    }

    override fun RBuilder.render() {
        if (state.image != null) {
            Image(state.image!!)
        }
    }
}

interface ConnectedProps : RProps {
    var match: RouteResultMatch<RouteParams>
}

fun viewContainer() = connect(
    connectedComponent = ViewContainer::class,
    mapStateToProps = { state: RootState ->
        images = state.images.images
    },
    passProps = { props: ConnectedProps ->
        match = props.match
    }
)
