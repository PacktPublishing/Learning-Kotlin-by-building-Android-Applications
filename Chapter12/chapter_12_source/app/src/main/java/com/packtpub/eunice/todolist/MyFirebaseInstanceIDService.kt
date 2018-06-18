package com.packtpub.eunice.todolist

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

class MyFirebaseInstanceIDService : FirebaseInstanceIdService(){
    override fun onTokenRefresh() {
        // Get updated InstanceID token.
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)
        //dHDDfhzDESA:APA91bEDEmX8MKxUXX_G5EZ1Q9xCbyoVUGqwCoV68f6cwZNN9SLURGq1m4TBS3n-au4sNn0Oj_Km3FOjDDYYwAX7KfTGr1EO0TIBIJa_Pkf2GGgJzGrivpn57lc8PNCZhxRERjpnPI-H

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
//        sendRegistrationToServer(refreshedToken)
    }

    companion object {
        val TAG = "MyFirebaseInstanceID"
    }
//    <p><a href="https://www2.realm.io/webinar/realm-platform-2-overview-success?utm_source=realm-email&amp;utm_medium=email&amp;wvideo=ywzwlm5rwp"><img src="https://embedwistia-a.akamaihd.net/deliveries/595e795e5d5bb92f1d5ac4b841b3e0d45c609ead.jpg?image_play_button_size=2x&amp;image_crop_resized=960x540&amp;image_play_button=1&amp;image_play_button_color=1d2340e0" width="400" height="225" style="width: 400px; height: 225px;"></a></p><p><a href="https://www2.realm.io/webinar/realm-platform-2-overview-success?utm_source=realm-email&amp;utm_medium=email&amp;wvideo=ywzwlm5rwp">Realm Platform 2.0: Overview and Demo</a></p>
}
