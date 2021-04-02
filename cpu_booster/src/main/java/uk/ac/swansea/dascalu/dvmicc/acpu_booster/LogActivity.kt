package uk.ac.swansea.dascalu.dvmicc.acpu_booster

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.StringBuilder

class LogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        readStolenData()
        findViewById<TextView>(R.id.logTextView).setTextIsSelectable(true)
    }

    private fun readStolenData() {
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