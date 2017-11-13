package common

import imports.*
import react.*
import kotlin.reflect.KClass

fun RBuilder.Router(handler: RHandler<RProps>) = child(BrowserRouterComponent::class, handler)

fun RBuilder.HashRouter(handler: RHandler<RProps>) = child(HashRouterComponent::class, handler)

fun RBuilder.Switch(handler: RHandler<RProps>) = child(SwitchComponent::class, handler)

fun RBuilder.Route(path: String, component: KClass<out React.Component<*, *>>, exact: Boolean = false) =
    child(RouteComponent::class) {
        attrs {
            this.path = path
            this.exact = exact
            this.component = component.js.unsafeCast<RClass<RProps>>()
        }
    }

fun RBuilder.RouteLink(to: String, classes: String? = null, handler: RHandler<RProps>) = child(LinkComponent::class) {
    attrs {
        this.to = to
        this.className = classes
    }
    handler()
}

fun RBuilder.NavLink(to: String,
                     classes: String? = null,
                     activeClasses: String? = null,
                     exact: Boolean = false,
                     handler: RHandler<RProps>) = child(NavLinkComponent::class) {
    attrs {
        this.to = to
        this.className = classes
        this.activeClassName = activeClasses
        this.exact = exact
    }
    handler()
}
