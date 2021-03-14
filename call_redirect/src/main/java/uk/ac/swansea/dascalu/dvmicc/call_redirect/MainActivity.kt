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

    private val requestStoragePermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { granted : Boolean ->

        if(granted) {
            Snackbar.make(findViewById(R.id.applyButton), R.string.storagePermissionAcquired,
                Snackbar.LENGTH_LONG).show()
            acquirePermissions()
        } else {
            Snackbar.make(findViewById(R.id.applyButton), R.string.fileStoragePermissionWarning,
                Snackbar.LENGTH_LONG).show()
        }
    }

    private val requestLowPermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { granted: Boolean ->

        if(granted) {
            Snackbar.make(findViewById(R.id.applyButton), R.string.processCallsPermissionAcquired,
                Snackbar.LENGTH_LONG).show()
        } else {
            Snackbar.make(findViewById(R.id.applyButton), R.string.processCallsPermissionDenied,
                Snackbar.LENGTH_LONG).show()
        }
    }

    private val requestImpossiblePermissionsLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()) { permissions ->

        if(permissions[Manifest.permission.READ_PHONE_STATE] == true) {
            Snackbar.make(findViewById(R.id.applyButton),
                R.string.readPhoneStatePermissionAcquired, Snackbar.LENGTH_LONG).show()
        } else if (permissions[Manifest.permission.READ_PHONE_STATE] == false) {
            Snackbar.make(findViewById(R.id.applyButton), R.string.readPhoneStatePermissionDenied,
                Snackbar.LENGTH_LONG).show()
        }

        if(permissions[Manifest.permission.CALL_PHONE] == true) {
            Snackbar.make(findViewById(R.id.applyButton), "Make phone calls permission granted!",
                Snackbar.LENGTH_LONG).show()
        } else if(permissions[Manifest.permission.CALL_PHONE] == false) {
            Snackbar.make(findViewById(R.id.applyButton), "App needs permission to make phone call!",
                Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
           == PackageManager.PERMISSION_GRANTED) {
           acquirePermissions()
       } else {
           requestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
       }

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
        val hasPhoneStatePermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED
        val hasMakeCallPermission = ContextCompat.checkSelfPermission(this,
            Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED
        val securityLevel = loadSecuritySettingsFromFile(this)

        if(!hasProcessCallsPermission && securityLevel == "low" ) {
            requestLowPermissionsLauncher.launch(Manifest.permission.PROCESS_OUTGOING_CALLS)
        } else if((!hasPhoneStatePermission || !hasMakeCallPermission) &&
            securityLevel == "impossible") {

            requestImpossiblePermissionsLauncher.launch(arrayOf(Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CALL_PHONE))
        }
    }
}