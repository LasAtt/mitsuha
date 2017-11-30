package redux

import kotlin.reflect.KClass
import kotlinext.js.jsObject
import react.*
import reducers.RootState
import reducers.Store

class ConnectedComponent<P: RProps, S: RState, C: RComponent<P, S>, R: RProps>(
    private val connectedComponent: KClass<C>,
    private val mapStateToProps: P.(state: RootState) -> Unit,
    private val mapDispatchToProps: P.(dispatch: Dispatch) -> Unit,
    private val passProps: P.(props: R) -> Unit
) : RComponent<R, RState>() {

    private val store = Store

    var currentState: P = jsObject { }

    private val cancelSubscription = Store.subscribe {
        this.setState { }
    }

    override fun shouldComponentUpdate(nextProps: R, nextState: RState): Boolean {
        val incomingState = jsObject<P> { }
        incomingState.mapStateToProps(store.state)

        if (incomingState != this.currentState) {
            this.currentState = incomingState
            return true
        }

        return false
    }

    override fun componentWillUnmount() {
        cancelSubscription()
    }

    override fun RBuilder.render() {
        child(connectedComponent) {
            attrs.mapStateToProps(store.state)
            attrs.mapDispatchToProps(store.dispatch)
            attrs.passProps(props)
        }
    }
}


fun <P: RProps, S: RState, C: RComponent<P, S>> connect(
    connectedComponent: KClass<C>,
    mapStateToProps: P.(state: RootState) -> Unit = { },
    mapDispatchToProps: P.(dispatch: Dispatch) -> Unit = { }
) = connect(connectedComponent, mapStateToProps, mapDispatchToProps, { _: RProps -> })

fun <P: RProps, S: RState, C: RComponent<P, S>, R: RProps> connect(
    connectedComponent: KClass<C>,
    mapStateToProps: P.(state: RootState) -> Unit = { },
    mapDispatchToProps: P.(dispatch: Dispatch) -> Unit = { },
    passProps: P.(props: R) -> Unit
): RClass<R> {
    val component = { ConnectedComponent(connectedComponent, mapStateToProps, mapDispatchToProps, passProps) } as RClass<R>
    return component.also { it.displayName = "ConnectedComponent" }
}
