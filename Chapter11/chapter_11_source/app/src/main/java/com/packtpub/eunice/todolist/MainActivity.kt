package com.packtpub.eunice.todolist

import android.app.DialogFragment
import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.os.AsyncTask
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ListView
import com.packtpub.eunice.todolist.db.TodoListDBContract
import com.packtpub.eunice.todolist.db.TodoListDBContract.DATABASE_NAME
import com.packtpub.eunice.todolist.db.room.AppDatabase
import com.packtpub.eunice.todolist.model.Task
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), NewTaskDialogFragment.NewTaskDialogListener {

    private var listView: ListView? = null

    private var listAdapter: TaskListAdapter? = null

    private var todoListItems = ArrayList<Task>()

    private var showMenuItems = false

    private var selectedItem = -1

    private var database: AppDatabase? = null

//    private var dbHelper: TodoListDBHelper = TodoListDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        listView = findViewById(R.id.list_view)

        database = Room.databaseBuilder(applicationContext, AppDatabase::class.java, DATABASE_NAME)
                .addMigrations(object : Migration(TodoListDBContract.DATABASE_VERSION - 1, TodoListDBContract.DATABASE_VERSION) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                    }
                }).build()

        populateListView()

        fab.setOnClickListener { showNewTaskUI() }

        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> showUpdateTaskUI(position) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.to_do_list_menu, menu)
        val editItem = menu.findItem(R.id.edit_item)
        val deleteItem = menu.findItem(R.id.delete_item)
        val completeItem = menu.findItem(R.id.mark_as_done_item)

        if (showMenuItems) {
            editItem.isVisible = true
            deleteItem.isVisible = true
            completeItem.isVisible = true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (-1 != selectedItem) {
            if (R.id.edit_item == item?.itemId) {

                val updateFragment = NewTaskDialogFragment.newInstance(R.string.update_task_dialog_title, todoListItems[selectedItem].taskDetails)
                updateFragment.show(fragmentManager, "updatetask")

            } else if (R.id.delete_item == item?.itemId) {
                val selectedTask = todoListItems[selectedItem]
                DeleteTaskAsyncTask(database, selectedTask).execute()
//                dbHelper.deleteTask(selectedTask)

                todoListItems.removeAt(selectedItem)
                listAdapter?.notifyDataSetChanged()
                selectedItem = -1
                Snackbar.make(fab, "Task deleted successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()

            } else if (R.id.mark_as_done_item == item?.itemId) {
                todoListItems[selectedItem].completed = true

                UpdateTaskAsyncTask(database, todoListItems[selectedItem]).execute()
                listAdapter?.notifyDataSetChanged()
                selectedItem = -1

                Snackbar.make(fab, "Task has been marked as completed", Snackbar.LENGTH_LONG).setAction("Action", null).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun showNewTaskUI() {

        val newFragment = NewTaskDialogFragment.newInstance(R.string.add_new_task_dialog_title, null)
        newFragment.show(fragmentManager, "newtask")
    }

    private fun showUpdateTaskUI(selected: Int) {

        selectedItem = selected
        showMenuItems = true
        invalidateOptionsMenu()
    }

    private fun populateListView() {
        todoListItems = RetrieveTasksAsyncTask(database).execute().get() as ArrayList<Task>
        listAdapter = TaskListAdapter(this, todoListItems)
        listView?.adapter = listAdapter
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, taskDetails:String) {

        if("newtask" == dialog.tag) {

            var addNewTask = Task(taskDetails, "")

            addNewTask.taskId = AddTaskAsyncTask(database, addNewTask).execute().get()
            todoListItems.add(addNewTask)
            listAdapter?.notifyDataSetChanged()

            Snackbar.make(fab, "Task Added Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()

        } else if ("updatetask" == dialog.tag) {
            todoListItems[selectedItem].taskDetails = taskDetails
//            dbHelper.updateTask(todoListItems[selectedItem])
            UpdateTaskAsyncTask(database, todoListItems[selectedItem]).execute()

            listAdapter?.notifyDataSetChanged()

            selectedItem = -1

            Snackbar.make(fab, "Task Updated Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }

    override fun onDestroy() {
//        dbHelper.close()
        super.onDestroy()
    }


    private class RetrieveTasksAsyncTask(private val database: AppDatabase?) : AsyncTask<Void, Void, List<Task>>() {

        override fun doInBackground(vararg params: Void): List<Task>? {
            return database?.taskDao()?.retrieveTaskList()
        }
    }

    private class AddTaskAsyncTask(private val database: AppDatabase?, private val newTask: Task) : AsyncTask<Void, Void, Long>() {

        override fun doInBackground(vararg params: Void): Long? {
            return database?.taskDao()?.addNewTask(newTask)
        }
    }

    private class UpdateTaskAsyncTask(private val database: AppDatabase?, private val selectedTask: Task) : AsyncTask<Void, Void, Unit>() {

        override fun doInBackground(vararg params: Void): Unit? {
            return database?.taskDao()?.updateTask(selectedTask)
        }
    }

    private class DeleteTaskAsyncTask(private val database: AppDatabase?, private val selectedTask: Task) : AsyncTask<Void, Void, Unit>() {

        override fun doInBackground(vararg params: Void): Unit? {
            return database?.taskDao()?.deleteTask(selectedTask)
        }
    }

}
