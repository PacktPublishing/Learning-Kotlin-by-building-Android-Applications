package com.packtpub.eunice.notesapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.crashlytics.android.Crashlytics
import io.fabric.sdk.android.Fabric
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fabric = Fabric.Builder(this)
                .kits(Crashlytics())
                .debuggable(true)           // Enables Crashlytics debugger
                .build()
        Fabric.with(fabric)

        crash_btn.setOnClickListener {
            Crashlytics.getInstance().crash()
        }
    }
}
