package com.packtpub.eunice.todolist.db

import android.provider.BaseColumns



/**
 * Created by eunice on 03/12/2017.
 */
object TodoListDBContract {

        const val DATABASE_VERSION = 2
        const val DATABASE_NAME = "todo_list_db"

    class TodoListItem: BaseColumns {
        companion object {
            const val TABLE_NAME = "todo_list_item"
            const val COLUMN_NAME_TASK = "task_details"
            const val COLUMN_NAME_DEADLINE = "task_deadline"
            const val COLUMN_NAME_COMPLETED = "task_completed"


        }
    }

}