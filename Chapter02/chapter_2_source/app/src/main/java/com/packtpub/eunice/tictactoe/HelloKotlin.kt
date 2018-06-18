package com.packtpub.eunice.tictactoe

import android.support.design.widget.Snackbar
import android.view.View


class HelloKotlin {

    fun displayKotlinMessage(view: View) {
        Snackbar.make(view, "Hello Kotlin!!", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

}