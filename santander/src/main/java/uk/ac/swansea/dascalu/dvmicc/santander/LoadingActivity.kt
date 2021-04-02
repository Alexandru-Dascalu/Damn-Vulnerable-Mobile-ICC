package uk.ac.swansea.dascalu.dvmicc.santander

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import java.util.Timer
import java.util.TimerTask

class LoadingActivity : AppCompatActivity() {
    private var timer : Timer? = null
    private var startTime : Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        timer = Timer()
        startTime = System.currentTimeMillis()

        val task = object : TimerTask() {
            override fun run() {
                if(System.currentTimeMillis() - startTime >= 5000) {
                    timer!!.cancel()
                    timer!!.purge()
                    timer = null

                    val intent = Intent(this@LoadingActivity, LogInActivity::class.java)
                    startActivity(intent)
                    this@LoadingActivity.finish()
                }
            }
        }

        timer!!.schedule(task, 10, 2500)
    }
}