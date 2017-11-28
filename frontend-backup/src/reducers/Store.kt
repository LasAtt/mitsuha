package reducers

import common.createLogger
import kotlinext.js.Object
import redux.Action
import redux.EnhancedReduxStore
import kotlin.js.Json
import kotlin.js.json

private fun actionTransformer(action: Action): Json {
    val asDynamic = action.asDynamic()
    val keys = Object.keys(action)
    return json(*keys.map { key ->
        val value = asDynamic[key]
        key to ((value as? HashSet<Any>)?.toJSON() ?: asDynamic[key])
    }.toTypedArray())
}

private fun stateTransformer(state: Any): Json {
    val asDynamic = state.asDynamic()
    val keys = Object.keys(state)
    return json(*keys.map { key ->
        val value = asDynamic[key]
        if (value is ReduxState) {
            return@map key to stateTransformer(value)
        }
        key to ((value as? HashSet<Any>)?.toJSON() ?: asDynamic[key])
    }.toTypedArray())
}

private val loggerOptions = json(
    "actionTransformer" to ::actionTransformer,
    "stateTransformer" to ::stateTransformer
)

object Store : EnhancedReduxStore<RootState>(::rootReducer, RootState(), arrayOf(createLogger(loggerOptions)))
