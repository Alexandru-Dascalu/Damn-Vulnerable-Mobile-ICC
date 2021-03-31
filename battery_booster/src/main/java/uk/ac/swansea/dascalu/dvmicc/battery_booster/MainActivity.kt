package uk.ac.swansea.dascalu.dvmicc.battery_booster

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainActivity : AppCompatActivity() {
    private val random = Random()
    private var boostProgress : Int = random.nextInt(15)
    private var timer : Timer? = null

    private val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted: Boolean ->

        if(granted) {
            Snackbar.make(findViewById(R.id.progressLevelTextView),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(findViewById(R.id.progressLevelTextView),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        updateProgressBar()
        getStoragePermission()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_appbar_layout, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.log_button -> {
                val intent = Intent(this, LogActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun getStoragePermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
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