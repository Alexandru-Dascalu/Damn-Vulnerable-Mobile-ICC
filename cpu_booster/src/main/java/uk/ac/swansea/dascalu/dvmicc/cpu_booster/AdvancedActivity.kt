package uk.ac.swansea.dascalu.dvmicc.cpu_booster

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

class AdvancedActivity :  AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advanced)
    }

    fun openWriteSettings(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            startActivity(intent)
        } else {
            Snackbar.make(view, "Only supported on Android 6 and up!",
                Snackbar.LENGTH_LONG).show()
        }
    }

    fun openOverlaySettings(view: View) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:$packageName"))
            startActivityForResult(intent, 0)
        } else {
            Snackbar.make(view, "Only supported on Android 6 and up!",
                Snackbar.LENGTH_LONG).show()
        }
    }

    fun openUsageAccessSettings(view: View) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
            startActivity(intent)
        } else {
            Snackbar.make(view, "Only supported on Android 6 and up!",
                Snackbar.LENGTH_LONG).show()
        }
    }
}