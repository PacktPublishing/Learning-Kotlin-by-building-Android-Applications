package com.packtpub.eunice.todolist

import android.app.Activity
import android.app.Dialog
import android.app.DialogFragment
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.EditText



class NewTaskDialogFragment: DialogFragment() {  // 1

    // 2
    interface NewTaskDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment, task: String)
        fun onDialogNegativeClick(dialog: DialogFragment)
    }

    var newTaskDialogListener: NewTaskDialogListener? = null  // 3

    // 4
    companion object {
        fun newInstance(title: Int, selected: String?): NewTaskDialogFragment { // 1

            val newTaskDialogFragment = NewTaskDialogFragment()
            val args = Bundle()
            args.putInt("dialog_title", title)
            args.putString("selected_item", selected) // 2
            newTaskDialogFragment.arguments = args
            return newTaskDialogFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val title = arguments.getInt("dialog_title")
        val selectedText = arguments.getString("selected_item") // 1
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)

        val dialogView = activity.layoutInflater.inflate(R.layout.dialog_new_task, null)

        val task = dialogView.findViewById<EditText>(R.id.task)

        task.setText(selectedText)  // 2


        builder.setView(dialogView)
                .setPositiveButton(R.string.save, { dialog, id ->

                    newTaskDialogListener?.onDialogPositiveClick(this, task.text.toString());
                })
                .setNegativeButton(android.R.string.cancel, { dialog, id ->

                    newTaskDialogListener?.onDialogNegativeClick(this)
                })

        return builder.create()
    }


    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        try {
            newTaskDialogListener = activity as NewTaskDialogListener  // 9
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement NewTaskDialogListener")
        }

    }
}