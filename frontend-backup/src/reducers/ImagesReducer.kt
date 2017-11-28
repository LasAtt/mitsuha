package reducers

import domain.Image
import redux.Action

sealed class ImagesAction : Action() {
    object Get : ImagesAction()
    data class GetSuccess(val images: Set<Image>) : ImagesAction()
    data class GetFailure(val error: dynamic, val message: String?) : ImagesAction()
}

data class ImagesState(
    val images: Set<Image> = setOf(),
    val fetching: Boolean = false
) : ReduxState

fun imagesReducer(state: ImagesState = ImagesState(), action: Action) = when(action) {
    is ImagesAction.Get -> state.copy(fetching = true)
    is ImagesAction.GetSuccess -> state.copy(
        images = action.images,
        fetching = false
    )
    is ImagesAction.GetFailure -> state.copy(fetching = false)
    else -> state
}
