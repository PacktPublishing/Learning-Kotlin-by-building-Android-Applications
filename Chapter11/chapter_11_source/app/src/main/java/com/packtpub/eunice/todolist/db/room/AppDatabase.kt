package com.packtpub.eunice.todolist.db.room

import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.Database
import com.packtpub.eunice.todolist.db.TodoListDBContract
import com.packtpub.eunice.todolist.model.Task
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.migration.Migration




/**
 * Created by eunice on 07/12/2017.
 */

@Database(entities = arrayOf(Task::class), version = TodoListDBContract.DATABASE_VERSION)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDAO
}