package app.mvvm.todo.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import app.mvvm.todo.data.TodoDatabase
import app.mvvm.todo.data.TodoRepositoryImple
import app.mvvm.todo.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TodoModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(app: Application): TodoDatabase {

        return Room.databaseBuilder(
            app,
            TodoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepository(db : TodoDatabase) : TodoRepository{
        return TodoRepositoryImple(db.dao)
    }
}