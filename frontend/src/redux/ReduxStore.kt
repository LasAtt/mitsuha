package redux

import kotlinext.js.jsObject

abstract class Action {
    val type = this::class.simpleName
}

class InitAction : Action()

interface EnhancerProps<T> {
    var getState: () -> T
    var dispatch: (Action) -> Action
}

fun <T> createStore(
    rootReducer: (state: T?, action: Action) -> T,
    preloadedState: T? = null,
    enhancers: Array<dynamic>? = null
): ReduxStore<T> {
    if (enhancers == null) {
        return ReduxStore(rootReducer, preloadedState)
    }

    return EnhancedReduxStore(rootReducer, preloadedState, enhancers)
}


open class ReduxStore<T>(val rootReducer: (state: T?, action: Action) -> T, preloadedState: T? = null) {
    var state: T = preloadedState ?: rootReducer(null, jsObject<InitAction> { })
        protected set

    var listeners: Set<() -> Unit> = setOf()

    open val dispatch = { action: Action ->
        state = rootReducer(state, action)
        action
    }

    fun subscribe(callback: () -> Unit): () -> Unit {
        listeners += callback
        return { listeners -= callback }
    }
}

class EnhancedReduxStore<T>(
    rootReducer: (state: T?, action: Action) -> T,
    preloadedState: T? = null,
    val enhancers: Array<out dynamic> = emptyArray()
) : ReduxStore<T>(rootReducer, preloadedState) {

    override val dispatch = { action: Action ->
        val getState = this::state
        val vanillaDispatch = super.dispatch
        val args = jsObject<EnhancerProps<T>> {
            this.getState = getState
            this.dispatch = vanillaDispatch
        }

        enhancers
            .plus({ super.dispatch })
            .map { it(args) }
            .reduceRight { enhancer, acc ->
                enhancer(acc)
            }(action)
    }
}
