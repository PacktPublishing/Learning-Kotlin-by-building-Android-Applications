package com.packtpub.eunice.todolist

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver: BroadcastReceiver() {
    val REQUEST_CODE = 12345
    val ACTION = "com.packtpub.eunice.todolist.alarm"

    override fun onReceive(context: Context?, p1: Intent?) {
        Log.d("onReceive", "p1$p1")
        val i = Intent(context, AlarmService::class.java)
        i.putExtra(TimePickerFragment.ARG_TASK_DESCRIPTION, p1?.getStringExtra(TimePickerFragment.ARG_TASK_DESCRIPTION))
        context?.startService(i)
    }
}