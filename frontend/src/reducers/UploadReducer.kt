package reducers

import domain.Image
import redux.Action

sealed class UploadActions : Action {
    class Upload : UploadActions()

    data class UploadSuccess(
        val image: Image
    ) : UploadActions()

    class UploadFailure: UploadActions()
}

data class UploadState(
    val id: Int,
    val name: String,
    val fetching: Boolean
)

fun uploadReducer(state: UploadState, action: Action) = when(action) {
    is UploadActions.Upload -> state.copy(fetching = true)
    is UploadActions.UploadSuccess -> state.copy(
        id = action.image.id,
        name = action.image.name,
        fetching = false
    )
    is UploadActions.UploadFailure -> state.copy(fetching = false)
    else -> state
}


