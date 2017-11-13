package reducers

import redux.Action

interface ReduxState

data class RootState(
    val images: ImagesState = ImagesState()
) : ReduxState

fun rootReducer(state: RootState?, action: Action): RootState {
    return state?.copy(
        images = imagesReducer(state.images, action)
    ) ?: RootState()
}

