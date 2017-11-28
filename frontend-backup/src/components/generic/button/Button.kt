package components.generic.button

import kotlinx.html.classes
import kotlinx.html.js.onClickFunction
import org.w3c.dom.events.Event
import react.*
import react.dom.button

@JsModule("src/components/generic/button/Button.css")
external val styles: ButtonStyles

external interface ButtonStyles {
    var button: String
}

interface ButtonProps : RProps {
    var classes: String
    var onClick: (Event) -> Any
    var disabled: Boolean
}

class Button(props: ButtonProps) : RComponent<ButtonProps, RState>(props) {

    override fun RBuilder.render() {
        button {
            attrs {
                classes = setOf(styles.button, props.classes)
                onClickFunction = { props.onClick(it) }
                disabled = props.disabled
            }
            children()
        }
    }
}

fun RBuilder.Button(classes: String = "", handler: RHandler<ButtonProps>) = child(Button::class) {
    attrs.classes = classes
    handler()
}
