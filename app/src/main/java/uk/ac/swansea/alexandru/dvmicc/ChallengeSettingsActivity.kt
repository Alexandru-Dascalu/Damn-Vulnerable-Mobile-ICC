package uk.ac.swansea.alexandru.dvmicc

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

import uk.ac.swansea.alexandru.dvmicc.model.Challenge

class ChallengeSettingsActivity :  AppCompatActivity() {
    private val requestStoragePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            applySettings()
        } else {
            val rootView = window.decorView.rootView
            Snackbar.make(rootView, R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_settings)

        val materialToolbar = findViewById<MaterialToolbar>(R.id.settingsActivityToolbar)
        setSupportActionBar(materialToolbar)

        title = resources.getString(R.string.challengeSettingsActivityTitle)
    }

    fun onApplySettings(view: View) {
        if(intent.extras != null) {
            val challenge : Challenge = intent.extras!!.get("challenge") as Challenge
            val launchedFromChallengeActivity : Boolean = intent.extras!!.get(
                    "launchedFromChallengeActivity") as Boolean

            if(!launchedFromChallengeActivity) {
                val intent = Intent(this, ChallengeActivity::class.java)
                intent.putExtra("challenge", challenge)
                startActivity(intent)
            } else {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
            }
        }

        applySettings()
        finish()
    }

    private fun applySettings() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val storageState = Environment.getExternalStorageState()

            if((storageState == Environment.MEDIA_MOUNTED)) {
                saveSecurityLevelsToFile()
            }
        } else {
            requestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
    }

    private fun saveSecurityLevelsToFile() {
        val storageState = Environment.getExternalStorageState()

        if((storageState == Environment.MEDIA_MOUNTED)) {
            val vulnerableSecurityLevelButtonGroup = findViewById<RadioGroup>(R.id
                    .vulnerableAppSecurityLevelRadioGroup)
            val malwareSecurityLevelButtonGroup = findViewById<RadioGroup>(R.id
                    .malwareSecurityLevelRadioGroup)

            val vulnerableAppSecurityLevel: String = findViewById<RadioButton>(
                    vulnerableSecurityLevelButtonGroup.checkedRadioButtonId).text.toString()
            val malwareSecurityLevel: String = findViewById<RadioButton>(
                    malwareSecurityLevelButtonGroup.checkedRadioButtonId).text.toString()

            val settingsFile = File(this.getExternalFilesDir(null), "dvmicc.txt")
            val writer = OutputStreamWriter(FileOutputStream(settingsFile))

            val settings : String = "%s\n%s".format(vulnerableAppSecurityLevel, malwareSecurityLevel)
            writer.write(settings)

            writer.close()
        }
    }
}