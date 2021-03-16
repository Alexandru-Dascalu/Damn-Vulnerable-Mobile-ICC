package uk.ac.swansea.dascalu.dvmicc.call_redirect

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.EditText

import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat

import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    companion object {
        val COUNTRY_CODE_REGEX = Regex("^\$|^\\d{0,3}[-\\s]\\d{0,3}|\\d{1,3}\$")
    }

    private val requestPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        if(permissions[Manifest.permission.PROCESS_OUTGOING_CALLS] == true) {
            Snackbar.make(findViewById(R.id.applyButton), R.string.processCallsPermissionAcquired,
                Snackbar.LENGTH_LONG).show()
        } else if(permissions[Manifest.permission.PROCESS_OUTGOING_CALLS] == false) {
            Snackbar.make(findViewById(R.id.applyButton), R.string.processCallsPermissionDenied,
                Snackbar.LENGTH_LONG).show()
        }

        if(permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == true) {
            Snackbar.make(findViewById(R.id.applyButton), R.string.storagePermissionAcquired,
                Snackbar.LENGTH_LONG).show()
        } else if(permissions[Manifest.permission.READ_EXTERNAL_STORAGE] == false) {
            Snackbar.make(findViewById(R.id.applyButton), R.string.fileStoragePermissionWarning,
                Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        acquirePermissions()
        setCountryCodeToEditText()
    }

    fun saveValidCountryCode(view: View) {
        val countryCodeEditText = findViewById<EditText>(R.id.countryCodeEditText)

        if(countryCodeEditText.text.toString().matches(COUNTRY_CODE_REGEX)) {
            saveCountryCode(countryCodeEditText.text.toString())
            Snackbar.make(view, "Country code saved!", Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(view, getString(R.string.invalidCodeWarning),
                    Snackbar.LENGTH_LONG).show()
        }
    }

    private fun saveCountryCode(countryCode: String) {
        val sharedPreferences = getSharedPreferences("country_code", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()

        editor.putString("country_code", countryCode)
        editor.apply()
    }

    private fun setCountryCodeToEditText() {
        val sharedPreferences = getSharedPreferences("country_code", Context.MODE_PRIVATE)
        val countryCode =  sharedPreferences.getString("country_code", null)

        if(countryCode != null) {
            val countryCodeEditText = findViewById<EditText>(R.id.countryCodeEditText)
            countryCodeEditText.setText(countryCode)
        }
    }

    private fun acquirePermissions() {
        val hasProcessCallsPermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.PROCESS_OUTGOING_CALLS) == PackageManager.PERMISSION_GRANTED
        val hasStoragePermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

        if(!hasProcessCallsPermission || !hasStoragePermission) {
            requestPermissionsLauncher.launch(arrayOf(Manifest.permission.PROCESS_OUTGOING_CALLS,
                Manifest.permission.READ_EXTERNAL_STORAGE))
        }
    }
}