package app.mvvm.todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo( todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)

    @Query("SELECT * from Todo where id = :id")
    suspend fun getTodoById(id : Int) : Todo?

    @Query("Select * from Todo")
    fun getTodos() : Flow<List<Todo>>
}