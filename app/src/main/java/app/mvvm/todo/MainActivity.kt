package app.mvvm.todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import app.mvvm.todo.ui.add_edit_todo.AddEditTodoScreen
import app.mvvm.todo.ui.theme.Todo_MVVMTheme
import app.mvvm.todo.ui.todo_list.TodoListScreen
import app.mvvm.todo.ui.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Todo_MVVMTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.TODO_LIST
                    ) {
                        composable(route = Routes.TODO_LIST) {

                            TodoListScreen(
                                onNavigate = {
                                    navController.navigate(it.route)
                                }
                            )
                        }

                        composable(
                            route = Routes.ADD_EDIT_TODO + "?todoId={todoId}",
                            arguments = listOf(
                                navArgument(name = "todoId") {
                                    type = NavType.IntType
                                    defaultValue = -1
                                    }
                            )
                        ) {
                                AddEditTodoScreen(onPopBackStack = {
                                    navController.popBackStack()
                                })
                        }
                    }
                }
            }
        }
    }
}

