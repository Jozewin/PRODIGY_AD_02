package app.mvvm.todo.ui.add_edit_todo

sealed class AddEditTodoEvent {
    data class OnTitleChange(val title : String) : AddEditTodoEvent()
    data class OnDescriptionChange(val title : String) : AddEditTodoEvent()
   object OnSaveTodoClick : AddEditTodoEvent(   )
}