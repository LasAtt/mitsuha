package reducers

import redux.Action

data class ReduxState(
    val images: ImagesState = ImagesState()
)

fun rootReducer(state: ReduxState?, action: Action) = state?.copy(
    images = imagesReducer(state.images, action)
) ?: ReduxState()

