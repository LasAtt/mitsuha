package reducers

import domain.Image
import redux.Action

sealed class ImagesAction : Action {
    object Get : ImagesAction()
    data class GetSuccess(val images: List<Image>): ImagesAction()
    data class GetFailure(val error: String?, val message: String?): ImagesAction()
}

data class ImagesState(
    val images: List<Image> = listOf(),
    val fetching: Boolean = false
)

fun imagesReducer(state: ImagesState = ImagesState(), action: Action) = when(action) {
    is ImagesAction.Get -> state.copy(fetching = true)
    is ImagesAction.GetSuccess -> state.copy(
        images = action.images,
        fetching = false
    )
    is ImagesAction.GetFailure -> state.copy(fetching = false)
    else -> state
}
