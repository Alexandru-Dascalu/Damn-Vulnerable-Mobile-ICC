package uk.ac.swansea.dascalu.dvmicc.swan_bank

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle

import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.google.android.material.snackbar.Snackbar

import java.util.Timer
import java.util.TimerTask

class LoadingActivity : AppCompatActivity() {
    private var timer : Timer? = null
    private var startTime : Long = 0

    private val readPermissionsLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { granted: Boolean ->

        if(granted) {
            Snackbar.make(findViewById(R.id.appNameTextView),
                    R.string.storagePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(findViewById(R.id.appNameTextView),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }

        startTimer()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            readPermissionsLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        } else {
            startTimer()
        }
    }

    private fun startTimer() {
        timer = Timer()
        startTime = System.currentTimeMillis()

        val task = object : TimerTask() {
            override fun run() {
                if(System.currentTimeMillis() - startTime >= 5000) {
                    timer!!.cancel()
                    timer!!.purge()
                    timer = null

                    runOnUiThread {
                        val intent = makeLogInIntent()
                        startActivity(intent)
                        this@LoadingActivity.finish()
                    }
                }
            }
        }

        timer!!.schedule(task, 10, 2500)
    }

    private fun makeLogInIntent() : Intent {
        val securityLevel = loadSecuritySettingsFromFile(this)

        if(securityLevel == "low") {
            val noPaymentUri : Uri = Uri.parse("santander_pay://uk.ac.swansea.dascalu.dvmicc.santander/pay")

            val intent = Intent(LOGIN_ACTION)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.setDataAndType(noPaymentUri, "text/plain")

            return intent
        } else {
            return Intent(this, LogInActivity::class.java)
        }
    }
}