package com.packtpub.eunice.tictactoe

import android.support.design.widget.Snackbar
import android.view.View


class HelloKotlin (private var message: String) {

    constructor(): this("Hello Kotlin!!")

    fun displayKotlinMessage(view: View) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

}