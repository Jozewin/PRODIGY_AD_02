package app.mvvm.todo.ui.util

sealed class UiEvent{
    object PopBackStack : UiEvent()

    data class Navigation(
        val route : String
    ) : UiEvent()

    data class ShowSnackBar(
        val message : String,
        val action : String? = null
    ) : UiEvent()
}
