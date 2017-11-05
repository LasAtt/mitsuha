package redux

interface Action

class InitAction : Action

abstract class ReduxStore<T>(val rootReducer: (state: T?, action: Action) -> T) {
    var state: T = rootReducer(null, InitAction())

    var listeners: Set<() -> Any> = setOf()

    fun dispatch(action: Action) {
        state = rootReducer(state, action)
    }

    fun subscribe(callback: () -> Any): () -> Unit {
        listeners += callback
        return { listeners -= callback }
    }
}
