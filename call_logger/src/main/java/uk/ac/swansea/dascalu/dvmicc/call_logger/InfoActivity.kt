package uk.ac.swansea.dascalu.dvmicc.call_logger

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.io.InputStreamReader
import java.lang.StringBuilder

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_broadcast_theft)

        val toolbar = findViewById<MaterialToolbar>(R.id.infoActivityToolbar)
        title = getString(R.string.malware_zone_title)
        setSupportActionBar(toolbar)

        populateStolenData()
    }

    private fun populateStolenData() {
        val dataTextView = findViewById<TextView>(R.id.stolenDataTextView)

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