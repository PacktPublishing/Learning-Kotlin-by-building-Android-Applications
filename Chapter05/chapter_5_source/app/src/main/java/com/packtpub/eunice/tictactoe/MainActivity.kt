package com.packtpub.eunice.tictactoe

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var gameBoard : Array<CharArray> = Array(3) { CharArray(3) } // 1
    var turn = 'X' // 2
    var tableLayout: TableLayout? = null // 3
    var turnTextView: TextView? = null // 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val fab = findViewById(R.id.fab) as FloatingActionButton
        fab.setOnClickListener { view -> HelloKotlin("Get ready for a fun game of Tic Tac Toe").displayKotlinMessage(view) }

        turnTextView = findViewById(R.id.turnTextView) as TextView  // 1

        tableLayout = findViewById(R.id.table_layout) as TableLayout // 2

        startNewGame(true)
    }


    private fun startNewGame(setClickListener: Boolean) {
        turn = 'X'
        turnTextView?.text = String.format(resources.getString(R.string.turn), turn)
        for (i in 0 until gameBoard.size) {
            for (j in 0 until gameBoard[i].size) {
                gameBoard[i][j] = ' '
                val cell = (tableLayout?.getChildAt(i) as TableRow).getChildAt(j) as TextView
                cell.text = ""

                if (setClickListener) {
                    cell.setOnClickListener(object : View.OnClickListener {
                        override fun onClick(v: View?) {
                            cellClickListener(i, j)
                        }
                    })
                }
            }
        }
    }

    private fun cellClickListener(row: Int, column: Int) {
        gameBoard[row][column] = turn
        ((tableLayout?.getChildAt(row) as TableRow).getChildAt(column) as TextView).text = turn.toString()
        turn = if ('X' == turn) 'O' else 'X'
        turnTextView?.text = String.format(resources.getString(R.string.turn), turn)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId


        return if (id == R.id.action_settings) {
            true
        } else super.onOptionsItemSelected(item)

    }
}
