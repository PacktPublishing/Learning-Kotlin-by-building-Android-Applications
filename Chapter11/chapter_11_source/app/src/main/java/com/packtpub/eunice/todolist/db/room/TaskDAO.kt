package com.packtpub.eunice.todolist.db.room

import android.arch.persistence.room.*
import com.packtpub.eunice.todolist.db.TodoListDBContract
import com.packtpub.eunice.todolist.model.Task



/**
 * Created by eunice on 06/12/2017.
 */
@Dao
interface TaskDAO {

    @Query("SELECT * FROM " + TodoListDBContract.TodoListItem.TABLE_NAME)
    fun retrieveTaskList(): List<Task>

    @Insert
    fun addNewTask(task: Task): Long

    @Update
    fun updateTask(task: Task)

    @Delete
    fun deleteTask(task: Task)

}