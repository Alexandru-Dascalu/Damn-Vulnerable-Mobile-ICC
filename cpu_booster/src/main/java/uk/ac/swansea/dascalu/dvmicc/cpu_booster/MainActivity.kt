package uk.ac.swansea.dascalu.dvmicc.cpu_booster

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

import androidx.appcompat.app.AppCompatActivity

import java.util.Random
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private val random = Random()
    private var boostProgress : Int = random.nextInt(15)
    private var timer : Timer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateProgressBar()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    fun startProgress(view: View) {
        timer = Timer()

        val task = object : TimerTask() {
            override fun run() {
                if(boostProgress != 100) {
                    val inc = random.nextInt(10)
                    boostProgress += inc

                    if(boostProgress > 100) {
                        boostProgress = 100
                    }

                    runOnUiThread {
                        updateProgressBar()
                    }
                } else {
                    timer!!.cancel()
                    timer!!.purge()
                    timer = null
                }
            }
        }

        timer!!.schedule(task, 300, 300)
    }

    fun startAdvancedActivity(view: View) {
        val intent = Intent(this, AdvancedActivity::class.java)
        startActivity(intent)
    }

    private fun updateProgressBar() {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = boostProgress

        val progressTextView = findViewById<TextView>(R.id.progressLevelTextView)

        if(boostProgress == 100) {
            progressTextView.text = "$boostProgress%\nPhone optimised"
        } else {
            progressTextView.text = "$boostProgress%"
        }
    }
}