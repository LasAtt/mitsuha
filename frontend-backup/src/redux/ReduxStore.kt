package redux

import kotlinext.js.jsObject

abstract class Action {
    val type = this::class.simpleName
}

interface EnhancerProps<T> {
    var getState: () -> T
    var dispatch: (Action) -> Action
}

typealias Dispatch = (Action) -> Action

open class ReduxStore<T>(val rootReducer: (state: T, action: Action) -> T, initialState: T) {
    var state: T = initialState
        protected set

    var listeners: Set<() -> Unit> = setOf()

    open val dispatch: Dispatch = { action: Action ->
        state = rootReducer(state, action)
        listeners.forEach { it() }
        action
    }

    fun subscribe(callback: () -> Unit): () -> Unit {
        listeners += callback
        return { listeners -= callback }
    }
}

open class EnhancedReduxStore<T>(
    rootReducer: (state: T, action: Action) -> T,
    initialState: T,
    private val enhancers: Array<out dynamic> = emptyArray()
) : ReduxStore<T>(rootReducer, initialState) {

    override val dispatch: Dispatch = { action: Action ->
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
