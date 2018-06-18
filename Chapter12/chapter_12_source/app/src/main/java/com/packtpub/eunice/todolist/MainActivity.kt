package com.packtpub.eunice.todolist

import android.app.DialogFragment
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), NewTaskDialogFragment.NewTaskDialogListener {

    private var listView: ListView? = null

    private var listAdapter: ArrayAdapter<String>? = null

    private var todoListItems = ArrayList<String>()

    private var showMenuItems = false

    private var selectedItem = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        listView = findViewById(R.id.list_view)

        populateListView()

        fab.setOnClickListener { showNewTaskUI() }

        listView?.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id -> showUpdateTaskUI(position) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.to_do_list_menu, menu)
        val editItem = menu.findItem(R.id.edit_item)
        val deleteItem = menu.findItem(R.id.delete_item)
        val reminderItem = menu.findItem(R.id.reminder_item)

        if (showMenuItems) {
            editItem.isVisible = true
            deleteItem.isVisible = true
            reminderItem.isVisible = true
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (-1 != selectedItem) {
            if (R.id.edit_item == item?.itemId) {

                val updateFragment = NewTaskDialogFragment.newInstance(R.string.update_task_dialog_title, todoListItems[selectedItem])
                updateFragment.show(fragmentManager, "updatetask")

            } else if (R.id.delete_item == item?.itemId) {

                todoListItems.removeAt(selectedItem)
                listAdapter?.notifyDataSetChanged()
                selectedItem = -1
                Snackbar.make(fab, "Task deleted successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()

            } else if (R.id.reminder_item == item?.itemId) {

                TimePickerFragment.newInstance(todoListItems[selectedItem])
                        .show(supportFragmentManager, "mainactivity")
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
        listAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, todoListItems)
        listView?.adapter = listAdapter
    }

    override fun onDialogPositiveClick(dialog: DialogFragment, task:String) {

        if("newtask" == dialog.tag) {
            todoListItems.add(task)
            listAdapter?.notifyDataSetChanged()

            Snackbar.make(fab, "Task Added Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()

        } else if ("updatetask" == dialog.tag) {
            todoListItems[selectedItem] = task

            listAdapter?.notifyDataSetChanged()

            selectedItem = -1

            Snackbar.make(fab, "Task Updated Successfully", Snackbar.LENGTH_LONG).setAction("Action", null).show()
        }
    }

    override fun onDialogNegativeClick(dialog: DialogFragment) {
    }


}
