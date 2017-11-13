@file:JsModule("react-router-dom")
package imports

import react.*

@JsName("HashRouter")
external class HashRouterComponent : React.Component<RProps, RState> {
    override fun render(): ReactElement?
}

@JsName("BrowserRouter")
external class BrowserRouterComponent : React.Component<RProps, RState> {
    override fun render(): ReactElement?
}

@JsName("Switch")
external class SwitchComponent : React.Component<RProps, RState> {
    override fun render(): ReactElement?
}

@JsName("Route")
external class RouteComponent : React.Component<RouteProps, RState> {
    override fun render(): ReactElement?
}

@JsName("Link")
external class LinkComponent : React.Component<LinkProps, RState> {
    override fun render(): ReactElement?
}

@JsName("NavLink")
external class NavLinkComponent : React.Component<NavLinkProps, RState> {
    override fun render(): ReactElement?
}

external interface RouteProps : RProps {
    var path: String
    var exact: Boolean
    var component: RClass<RProps>
}

external interface LinkProps : RProps {
    var to: String
    var className: String?
}

external interface NavLinkProps : RProps {
    var to: String
    var className: String?
    var activeClassName: String?
    var exact: Boolean
}

external interface RouteResultProps<T : RProps> : RProps {
    var match: RouteResultMatch<T>
}

external interface RouteResultMatch<T : RProps> {
    var url: String
    var path: String
    var params: T
    var isExact: Boolean
}
