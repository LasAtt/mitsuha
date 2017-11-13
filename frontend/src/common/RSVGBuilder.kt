package common

import kotlinext.js.jsObject
import org.w3c.dom.events.Event
import react.*
import react.dom.fixAttributeName

@ReactDsl
class RSVGBuilder(private val tagName: String) : RBuilder() {
    fun setProp(attribute: String, value: Any?) {
        val key = fixAttributeName(attribute)
    }

    var key: String
        get() = props.key
        set(value) {
            props.key = value

        }

    val props: SVGProps = jsObject { }

    inline fun attrs(handler: SVGProps.() -> Unit) {
        props.handler()
    }

    fun ref(handler: (dynamic) -> Unit) {
        props.ref = handler
    }


    fun create(): ReactElement = React.createElement(tagName, props, *childList.toTypedArray())
}

inline fun RBuilder.svgTag(tagName: String, classes: String? = null, block: RSVGBuilder.() -> Unit): ReactElement =
    child(RSVGBuilder(tagName).apply {
        block()
        props.className = classes
    }.create())

interface SVGProps : RProps {
    var x: Int?
    var y: Int?
    var width: Int?
    var height: Int?
    var className: String?
    var rx: Int?
    var ry: Int?
    var x1: Int?
    var x2: Int?
    var y1: Int?
    var y2: Int?
    var ref: ((dynamic) -> Unit)?
    var onClick: ((Event) -> Any)?
    var onContextMenu: ((Event) -> Any)?
    var onDoubleClick: ((Event) -> Any)?
    var onDrag: ((Event) -> Any)?
    var onDragEnd: ((Event) -> Any)?
    var onDragEnter: ((Event) -> Any)?
    var onDragExit: ((Event) -> Any)?
    var onDragLeave: ((Event) -> Any)?
    var onDragOver: ((Event) -> Any)?
    var onDragStart: ((Event) -> Any)?
    var onDrop: ((Event) -> Any)?
    var onMouseDown: ((Event) -> Any)?
    var onMouseEnter: ((Event) -> Any)?
    var onMouseLeave: ((Event) -> Any)?
    var onMouseMove: ((Event) -> Any)?
    var onMouseOut: ((Event) -> Any)?
    var onMouseOver: ((Event) -> Any)?
    var onMouseUp: ((Event) -> Any)?
    var onTransitionEnd: ((Event) -> Any)?
    var onAnimationStart: ((Event) -> Any)?
    var onAnimationEnd: ((Event) -> Any)?
    var onAnimationIteration: ((Event) -> Any)?
    var accentHeight: Int?
    var color: String?
    var cursor: String?
    var cx: Int?
    var cy: Int?
    var d: String?
    var direction: String?
    var display: String?
    var dx: Int?
    var dy: Int?
    var fx: String?
    var fy: String?
    var length: String?
    var href: String?
    var stroke: String?
    var strokeWidth: Int?
    var offset: String?
    var stopColor: String?
    var path: String?
}
