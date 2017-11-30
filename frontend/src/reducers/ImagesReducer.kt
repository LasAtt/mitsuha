package reducers

import domain.Image
import redux.Action

sealed class ImagesAction : Action() {
    data class AddAll(val images: Set<Image>) : ImagesAction()
    data class Error(val error: dynamic, val message: String?) : ImagesAction()
}

data class ImagesState(
    val images: Set<Image> = setOf()
) : ReduxState

fun imagesReducer(state: ImagesState = ImagesState(), action: Action) = when(action) {
    is ImagesAction.AddAll -> state.copy(
        images = action.images
    )
    is ImagesAction.Error -> state.copy()
    else -> state
}
