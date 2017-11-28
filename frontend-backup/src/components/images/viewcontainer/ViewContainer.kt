@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package components.images.viewcontainer

import api.getImage
import common.RouteResultMatch
import common.launch
import components.images.image.Image
import domain.Image
import react.*
import reducers.Store

interface ViewProps : RProps {
    var match: RouteResultMatch<Params>

    interface Params : RProps {
        var id: Int
    }
}

interface ViewState : RState {
    var id: Int
    var image: Image?

    //Redux
    var images: Set<Image>
}

class ViewContainer(props: ViewProps) : RComponent<ViewProps, ViewState>(props) {

    override fun ViewState.init(props: ViewProps) {
        this.id = props.match.params.id
    }

    val subscription = Store.subscribe {
        setState {
            images = Store.state.images.images
        }
    }

    override fun componentDidMount() {
        launch {
            val image = getImage(state.id)
            setState {
                this.image = image
            }
        }
    }

    override fun componentWillUnmount() {
        subscription()
    }

    override fun RBuilder.render() {
        if (state.image != null) {
            Image(state.image!!)
        }
    }
}


