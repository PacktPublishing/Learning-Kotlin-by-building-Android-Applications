package com.packtpub.eunice.todolist

import android.app.IntentService
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.util.Log


class AlarmService : IntentService("ToDoListAppAlarmReceiver") {
    private var context: Context? = null

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun onHandleIntent(intent: Intent?) {
        intent?.getStringExtra(TimePickerFragment.ARG_TASK_DESCRIPTION)?.let { showNotification(it) }

        if(null == intent){
            Log.d("AlarmService", "onHandleIntent( OH How? )")
        }
    }

    private fun showNotification(taskDescription: String) {
        Log.d("AlarmService", "showNotification($taskDescription)")
        val CHANNEL_ID = "todolist_alarm_channel_01"
        val mBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_active_black_48dp)
                .setContentTitle("Roso")
                .setContentText(taskDescription)

        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        mNotificationManager.notify(23, mBuilder.build())
    }
}

/*
* Start alarm in say dialog, ---- check if it still works after app is cleared
* then have pendingIntent start a receiver which will show a notification
*
* or
*
* Start alarm in Intent service,
* and continue as first
*
* So since it's the intent service's handler which don't sleep, it will handle the notification?
* Well it makes sense, - cos it can't be handled by the activity, right?
* So I guess we will rather start the broadcast receiver, the br will wait for alarm, and send
* broadcast when alarm fires, then the intent service picks it up...? or rather the IS is started
* by the BR and shows notification
* ...?
*
*
* Doesn't wake up screen
* Pass items through pending intent through receiver to intent service
* Test with context receiver &/ try removing manifest receiver - why because
* If your app targets API level 26 or higher, you cannot use the manifest to declare a receiver for
* most implicit broadcasts (broadcasts that do not target your app specifically).
*
*
* Add icon to edit menu,
*
* */