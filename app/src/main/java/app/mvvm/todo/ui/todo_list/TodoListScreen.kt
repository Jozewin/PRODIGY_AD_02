package app.mvvm.todo.ui.todo_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import app.mvvm.todo.ui.util.UiEvent

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    onNavigate: (UiEvent.Navigation) -> Unit,
    viewModel: TodoListViewModel = hiltViewModel()
) {

    // Collecting this as state from flow then only every changes will take as recomposition
    val todos = viewModel.todos.collectAsState(initial = emptyList())


    val snackBarHostState = remember {
        SnackbarHostState()
    }
    LaunchedEffect(key1 = true) {


        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigation -> onNavigate(event)
                is UiEvent.ShowSnackBar -> {
                    val result = snackBarHostState.showSnackbar(
                        message = event.message,
                        actionLabel = event.action,
                        duration = SnackbarDuration.Short
                    )

                    if (result == SnackbarResult.ActionPerformed) {
                        viewModel.onEvent(TodoListEvent.OnDeleteUndone)
                    }
                }

                else -> Unit
            }

        }


    }


    Scaffold(
        snackbarHost = { SnackbarHost(snackBarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    viewModel.onEvent(TodoListEvent.OnAddTodoClick)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Todo"
                )
            }
        }

    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()){
            items(todos.value){todos ->
                TodoList(
                    todo = todos,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            viewModel.onEvent(TodoListEvent.OnTodoClick(todos))
                        }
                        .padding(16.dp)
                )

            }
        }

    }
}