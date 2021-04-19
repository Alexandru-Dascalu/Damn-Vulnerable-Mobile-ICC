package uk.ac.swansea.dascalu.dvmicc.home

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
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat

import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.FileOutputStream
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.lang.IllegalStateException
import java.util.Locale

import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge
import uk.ac.swansea.dascalu.dvmicc.home.model.ViewModel

class ChallengeSettingsActivity :  AppCompatActivity() {
    private var challenge: Challenge = ViewModel.instance.challenge

    private val writeRequestStoragePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            applySettings()
        } else {
            Snackbar.make(findViewById(R.id.vulnerableAppSecurityLevelRadioGroup),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    private val readRequestStoragePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            loadSecuritySettingsFromFile()
        } else {
            Snackbar.make(findViewById(R.id.vulnerableAppSecurityLevelRadioGroup),
                    R.string.fileStoragePermissionWarning, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_settings)

        val materialToolbar = findViewById<MaterialToolbar>(R.id.settingsActivityToolbar)
        setSupportActionBar(materialToolbar)

        title = resources.getString(R.string.challengeSettingsActivityTitle)

        disableNecessaryLevelRadioButtons()
        initialiseSecuritySettings()
    }

    private fun disableNecessaryLevelRadioButtons() {
        if (!challenge.securityLevels.containsKey("low")) {
            findViewById<RadioButton>(R.id.radio_button_vulnerable_low).isEnabled = false
        }

        if (!challenge.securityLevels.containsKey("medium")) {
            findViewById<RadioButton>(R.id.radio_button_vulnerable_medium).isEnabled = false
        }

        if (!challenge.securityLevels.containsKey("high")) {
            findViewById<RadioButton>(R.id.radio_button_vulnerable_high).isEnabled = false
        }

        if (!challenge.securityLevels.containsKey("very high")) {
            findViewById<RadioButton>(R.id.radio_button_vulnerable_very_high).isEnabled = false
        }

        if (!challenge.securityLevels.containsKey("impossible")) {
            findViewById<RadioButton>(R.id.radio_button_vulnerable_impossible).isEnabled = false
        }
    }

    fun onApplySettings(view: View) {
        applySettings()
        if(intent.extras != null) {
            val launchedFromChallengeActivity : Boolean = intent.extras!!.get(
                    "launchedFromChallengeActivity") as Boolean

            if(!launchedFromChallengeActivity) {
                val intent = Intent(this, ChallengeActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent()
                setResult(Activity.RESULT_OK, intent)
            }
        }

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
            writeRequestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun saveSecurityLevelsToFile() {
        val storageState = Environment.getExternalStorageState()

        if((storageState == Environment.MEDIA_MOUNTED)) {
            val vulnerableSecurityLevelButtonGroup = findViewById<RadioGroup>(R.id
                    .vulnerableAppSecurityLevelRadioGroup)
            val malwareSecuritySwitch = findViewById<SwitchCompat>(R.id.malwareSecuritySwitch)

            val vulnerableAppSecurityLevel: String = findViewById<RadioButton>(
                    vulnerableSecurityLevelButtonGroup.checkedRadioButtonId).text.toString().toLowerCase(Locale.ROOT)
            val malwareSecurityOvercome: Boolean = malwareSecuritySwitch.isChecked

            val settingsFile = File(this.getExternalFilesDir(null), "dvmicc.txt")
            val writer = OutputStreamWriter(FileOutputStream(settingsFile))

            val challengeIndex : Int = if (challenge != null) {
                challenge!!.challengeNameIndex
            } else {
                -1
            }

            val settings : String = "%d\n%s\n%b".format(challengeIndex, vulnerableAppSecurityLevel,
                    malwareSecurityOvercome)
            writer.write(settings)

            writer.close()
        }
    }

    private fun initialiseSecuritySettings() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val storageState = Environment.getExternalStorageState()

            if((storageState == Environment.MEDIA_MOUNTED) || (storageState == Environment.
                    MEDIA_MOUNTED_READ_ONLY)) {
                loadSecuritySettingsFromFile()
            }
        } else {
            readRequestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun loadSecuritySettingsFromFile() {
        val storageState = Environment.getExternalStorageState()

        if((storageState == Environment.MEDIA_MOUNTED)) {
            val vulnerableSecurityLevelButtonGroup = findViewById<RadioGroup>(
                    R.id.vulnerableAppSecurityLevelRadioGroup)
            val malwareSecuritySwitch = findViewById<SwitchCompat>(R.id.malwareSecuritySwitch)

            val settingsFile = File(this.getExternalFilesDir(null), "dvmicc.txt")

            if (settingsFile.exists()) {
                val reader = BufferedReader(InputStreamReader(FileInputStream(settingsFile)))

                reader.readLine()
                val vulnerableAppSecurityLevel = reader.readLine().toLowerCase(Locale.ROOT)
                val malwareSecurityLevel = reader.readLine().toLowerCase(Locale.ROOT)
                reader.close()

                when(vulnerableAppSecurityLevel) {
                    "low" -> {
                        vulnerableSecurityLevelButtonGroup.check(R.id.radio_button_vulnerable_low)
                    }
                    "medium" -> {
                        vulnerableSecurityLevelButtonGroup.check(R.id.radio_button_vulnerable_medium)
                    }
                    "high" -> {
                        vulnerableSecurityLevelButtonGroup.check(R.id.radio_button_vulnerable_high)
                    }
                    "very high" -> {
                        vulnerableSecurityLevelButtonGroup.check(R.id.radio_button_vulnerable_very_high)
                    }
                    "impossible" -> {
                        vulnerableSecurityLevelButtonGroup.check(R.id.radio_button_vulnerable_impossible)
                    } else -> {
                        throw IllegalStateException("Security level in file has invalid value!")
                    }
                }

                malwareSecuritySwitch.isChecked = malwareSecurityLevel.toBoolean()
            }
        } else {
            Snackbar.make(findViewById(R.id.vulnerableAppSecurityLevelRadioGroup), "External storage is not present!",
                    Snackbar.LENGTH_LONG).show()
        }
    }
}