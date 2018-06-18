package com.packtpub.eunice.todolist

import android.app.AlarmManager
import android.app.Dialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.util.Log
import android.widget.TimePicker
import android.widget.Toast
import java.util.*


class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {
    private var taskDescription: String? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        taskDescription = savedInstanceState?.getString(ARG_TASK_DESCRIPTION)

        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        return TimePickerDialog(activity, this, hour, minute,
                DateFormat.is24HourFormat(activity))
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        Log.d("onTimeSet", "hourOfDay: $hourOfDay minute:$minute")

        Toast.makeText(activity, "Reminder set successfully", Toast.LENGTH_LONG).show()

        val intent = Intent(activity, AlarmReceiver::class.java)//applicationContext for as long
        // as app runs
        intent.putExtra(ARG_TASK_DESCRIPTION, taskDescription)

        val alarmIntent = PendingIntent.getBroadcast(activity, 0, intent, 0)
        val alarmMgr = activity.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
//
        alarmMgr.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, alarmIntent)
        alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent)
    }

    companion object {
         val ARG_TASK_DESCRIPTION = "task-description"

        fun newInstance(taskDescription: String): TimePickerFragment {
            val fragment = TimePickerFragment()
            val args = Bundle()
            args.putString(ARG_TASK_DESCRIPTION, taskDescription)
            fragment.arguments = args
            return fragment
        }
    }
}