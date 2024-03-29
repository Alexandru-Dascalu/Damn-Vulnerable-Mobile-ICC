package uk.ac.swansea.dascalu.dvmicc.money_manager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.StringBuilder

class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        val appBar = findViewById<MaterialToolbar>(R.id.logActivityToolbar)
        title = resources.getString(R.string.log)
        setSupportActionBar(appBar)

        readLog()
        findViewById<TextView>(R.id.logTextView).setTextIsSelectable(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.log_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.clear_log_button -> {
                deleteFile("data.txt")
                readLog()

                val view = findViewById<ScrollView>(R.id.logScrollView)
                Snackbar.make(view, getString(R.string.log_cleared), Snackbar.LENGTH_LONG).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun readLog() {
        val dataTextView = findViewById<TextView>(R.id.logTextView)

        try {
            openFileInput("data.txt").use {
                val reader = BufferedReader(InputStreamReader(it))
                val stringBuilder = StringBuilder()

                var line : String? = reader.readLine()
                while(line != null) {
                    stringBuilder.append(line)
                    stringBuilder.append("\n")
                    line = reader.readLine()
                }

                reader.close()
                dataTextView.text = stringBuilder.toString()
            }
        } catch (e: FileNotFoundException) {
            dataTextView.text = getString(R.string.no_data)
        }
    }
}