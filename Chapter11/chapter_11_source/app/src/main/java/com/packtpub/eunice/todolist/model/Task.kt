package com.packtpub.eunice.todolist.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import android.provider.BaseColumns
import com.packtpub.eunice.todolist.db.TodoListDBContract

/**
 * Created by eunice on 04/12/2017.
 */
@Entity(tableName = TodoListDBContract.TodoListItem.TABLE_NAME)
class Task() {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = BaseColumns._ID)
    var taskId: Long? = null

    @ColumnInfo(name = TodoListDBContract.TodoListItem.COLUMN_NAME_TASK)
    var taskDetails: String? = null

    @ColumnInfo(name = TodoListDBContract.TodoListItem.COLUMN_NAME_DEADLINE)
    var taskDeadline: String? = null

    @ColumnInfo(name = TodoListDBContract.TodoListItem.COLUMN_NAME_COMPLETED)
    var completed: Boolean? = false

    @Ignore
    constructor(taskDetails: String?, taskDeadline: String?): this() {
        this.taskDetails = taskDetails
        this.taskDeadline = taskDeadline
    }

    constructor(taskId:Long, taskDetails: String?, taskDeadline: String?, completed: Boolean) : this(taskDetails, taskDeadline) {
        this.taskId = taskId
        this.completed = completed
    }

}