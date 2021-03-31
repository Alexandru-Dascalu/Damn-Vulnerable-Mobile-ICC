package uk.ac.swansea.dascalu.dvmicc.battery_booster

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar

import java.util.Random
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private val random = Random()
    //duration is measured in seconds
    private var optimiseDuration : Int = 0
    private var startOptimiseTime : Long = 0
    private var isOptimised : Boolean = false
    private var timer : Timer? = null

    private val requestPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted: Boolean ->

        if(granted) {
            Snackbar.make(findViewById(R.id.optimiseTextView),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(findViewById(R.id.optimiseTextView),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setBatteryIssues()
        stopProgressBar()
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

    private fun setBatteryIssues() {
        val issues : Int = random.nextInt(25)
        findViewById<TextView>(R.id.optimiseTextView).text = resources.getString(
                R.string.battery_issues_text, issues)
    }

    fun startProgress(view: View) {
        if(!isOptimised) {
            optimiseDuration = random.nextInt(20)
            startOptimiseTime = System.currentTimeMillis()

            val progressBar = findViewById<LinearProgressIndicator>(R.id.progressBar)
            progressBar.showAnimationBehavior = LinearProgressIndicator.SHOW_INWARD
            progressBar.visibility = View.VISIBLE


            timer = Timer()

            val task = object : TimerTask() {
                override fun run() {
                    if(System.currentTimeMillis() - startOptimiseTime >= optimiseDuration * 1000) {
                        runOnUiThread {
                            stopProgressBar()
                            findViewById<TextView>(R.id.optimiseTextView).text = resources.getString(
                                    R.string.optimsed_text)
                        }

                        isOptimised = true
                        timer!!.cancel()
                        timer!!.purge()
                        timer = null
                    }
                }
            }

            timer!!.schedule(task, 10, 300)
        }
    }

    fun startAdvancedActivity(view: View) {
        val intent = Intent(this, AdvancedActivity::class.java)
        startActivity(intent)
    }

    private fun stopProgressBar() {
        val progressBar = findViewById<LinearProgressIndicator>(R.id.progressBar)
        progressBar.showAnimationBehavior = LinearProgressIndicator.SHOW_NONE
        progressBar.visibility = View.INVISIBLE
    }
}