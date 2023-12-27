package app.mvvm.todo.ui.todo_list

import app.mvvm.todo.data.Todo

sealed class TodoListEvent {
    data class DeleteTodo(val todo: Todo) : TodoListEvent()
    data class OnDoneChange(val todo: Todo, val isDone : Boolean) : TodoListEvent()
    data class OnTodoClick(val todo: Todo) : TodoListEvent()
    object OnAddTodoClick : TodoListEvent()
    object OnDeleteUndone : TodoListEvent()

}
