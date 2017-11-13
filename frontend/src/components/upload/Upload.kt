@file:Suppress("EXPERIMENTAL_FEATURE_WARNING")

package components.upload

import api.sendImage
import common.launch
import components.generic.button.Button
import imports.history
import kotlinx.html.InputType
import kotlinx.html.js.onChangeFunction
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.events.Event
import org.w3c.dom.url.URL
import org.w3c.files.File
import react.*
import react.dom.div
import react.dom.img
import react.dom.input

@JsModule("src/components/upload/Upload.css")
external val styles: UploadStyle

interface UploadStyle {
    val imageDiv: String
    val imgTag: String
}

interface UploadProps : RProps {
    var history: history
}

interface UploadState : RState {
    var file: File?
}

class Upload(props: UploadProps) : RComponent<UploadProps, UploadState>(props) {

    private fun onChangeFile(event: Event) {
        val target = event.target
        if (target is HTMLInputElement) {
            setState {
                this.file = target.files!!.item(0)
            }
        }
    }

    private fun onUploadFile() {
        val file = state.file ?: return
        launch {
            val image = sendImage(file)
            props.history.replace("/images/${image.id}")
        }
    }

    override fun RBuilder.render() {
        context
        div {
            div {
                input {
                    attrs {
                        type = InputType.file
                        onChangeFunction = this@Upload::onChangeFile
                    }
                }
                Button {
                    attrs {
                        disabled = state.file == null
                        onClick = { onUploadFile() }
                    }
                    +"Send file"
                }
            }
            if(state.file != null) {
                val image = state.file!!
                div(styles.imageDiv) {
                    img(alt = image.name, classes = styles.imgTag) {
                        attrs.src = URL.createObjectURL(image)
                    }
                }
            }
        }
    }
}
