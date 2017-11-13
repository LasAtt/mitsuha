//@file:JsModule("redux")
//
//package imports
//
//import reducers.Action
//
//external fun <T> createStore(
//    rootReducer: (T, Action) -> T,
//    preloadedState: T? = definedExternally,
//    enhancer: dynamic = definedExternally
//): Store<T>
//
//external class Store<T> {
//    fun getState(): T
//
//    fun dispatch(action: Action)
//
//    fun subscribe(callback: () -> Unit)
//
//    fun replaceReducer(nextReducer: (Action) -> T)
//}
//
//external fun applyMiddleware(vararg middleware: dynamic): dynamic
//
//
