package app.mvvm.todo.ui.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mvvm.todo.data.Todo
import app.mvvm.todo.data.TodoRepository
import app.mvvm.todo.ui.util.Routes
import app.mvvm.todo.ui.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoListViewModel @Inject constructor(
    private val todoRepository: TodoRepository
) : ViewModel() {

    val todos = todoRepository.getTodos()

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    private var deleteTodo: Todo? = null
    fun onEvent(todoEvent: TodoListEvent) {
        when (todoEvent) {
            is TodoListEvent.DeleteTodo -> {
                viewModelScope.launch {
                    deleteTodo = todoEvent.todo
                    todoRepository.deleteTodo(todoEvent.todo)

                    sendUiEvent(UiEvent.ShowSnackBar(
                        "Todo is deleted",
                        action = "Undo"
                    ))
                }
            }

            TodoListEvent.OnAddTodoClick -> {

                sendUiEvent(UiEvent.Navigation(Routes.ADD_EDIT_TODO))
            }

            TodoListEvent.OnDeleteUndone -> {
                deleteTodo?.let {
                    viewModelScope.launch {
                        todoRepository.insertTodo(it)
                    }
                }
            }

            is TodoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    todoRepository.insertTodo(
                        todoEvent.todo.copy(
                            isDone = todoEvent.isDone
                        )
                    )
                }
            }

            is TodoListEvent.OnTodoClick -> {
                sendUiEvent(UiEvent.Navigation(Routes.ADD_EDIT_TODO + "?todoId=${todoEvent.todo.id}"))
            }

            else -> {
                Unit
            }
        }
    }

    private fun sendUiEvent(event: UiEvent){
        viewModelScope.launch {
            _uiEvent.send(event)
        }
    }
}