package common

import react.*
import kotlin.reflect.KClass

fun RBuilder.hashRouter(handler: RHandler<RProps>) = child(HashRouterComponent::class, handler)

fun RBuilder.switch(handler: RHandler<RProps>) = child(SwitchComponent::class, handler)

fun RBuilder.route(path: String, component: KClass<out React.Component<*, *>>, exact: Boolean = false) =
        child(RouteComponent::class) {
            attrs {
                this.path = path
                this.exact = exact
                this.component = component.js.unsafeCast<RClass<RProps>>()
            }
        }

fun RBuilder.routeLink(to: String, classes: String? = null, handler: RHandler<RProps>) = child(LinkComponent::class) {
    attrs {
        this.to = to
        this.className = classes
    }
    handler()
}
