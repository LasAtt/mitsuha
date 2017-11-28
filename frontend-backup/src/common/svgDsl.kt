package common

import react.RBuilder


fun RBuilder.rect(classes: String? = null, handler: RSVGBuilder.() -> Unit) =
    svgTag("rect", classes, handler)

fun RBuilder.line(classes: String? = null, handler: RSVGBuilder.() -> Unit) =
    svgTag("line", classes, handler)
