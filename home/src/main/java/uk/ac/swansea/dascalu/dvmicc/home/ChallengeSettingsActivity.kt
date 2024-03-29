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
import com.google.android.material.switchmaterial.SwitchMaterial

import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.FileOutputStream
import java.io.BufferedReader
import java.io.OutputStreamWriter
import java.lang.IllegalStateException
import java.util.Locale

import uk.ac.swansea.dascalu.dvmicc.home.model.Challenge
import uk.ac.swansea.dascalu.dvmicc.home.model.ChallengeViewModel
import uk.ac.swansea.dascalu.dvmicc.home.model.OperationMode

class ChallengeSettingsActivity :  AppCompatActivity() {
    private val writeRequestStoragePermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
        if (isGranted) {
            applySecuritySettings()
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

    private val operationModeChangeListener = RadioGroup.OnCheckedChangeListener { _: RadioGroup?, checkedId: Int ->
        val malwareEnabledSwitch = findViewById<SwitchMaterial>(R.id.malwareEnabledSwitch)
        if (checkedId == R.id.radio_button_make_own_malware) {
            malwareEnabledSwitch.isChecked = true
            malwareEnabledSwitch.isEnabled = false
        } else {
            malwareEnabledSwitch.isEnabled = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_challenge_settings)

        val materialToolbar = findViewById<MaterialToolbar>(R.id.settingsActivityToolbar)
        setSupportActionBar(materialToolbar)

        val operationModeRadioGroup = findViewById<RadioGroup>(R.id.operationModeRadioGroup)
        operationModeRadioGroup.setOnCheckedChangeListener(operationModeChangeListener)

        title = resources.getString(R.string.challengeSettingsActivityTitle)

        setOperationModeSelection(intent)
        disableUnnecessarySecurityRadioButtons()
        initialiseSecuritySettings()
    }

    private fun setOperationModeSelection(intent: Intent) {
        if (intent.extras != null && intent.getSerializableExtra("mode") != null) {
            val operationModeRadioGroup = findViewById<RadioGroup>(R.id.operationModeRadioGroup)

            when (intent.getSerializableExtra("mode") as OperationMode) {
                OperationMode.BEGINNER -> operationModeRadioGroup.check(R.id.radio_button_beginner)
                OperationMode.EXPERIENCED -> operationModeRadioGroup.check(R.id.radio_button_experienced)
                OperationMode.MAKE_OWN_MALWARE -> operationModeRadioGroup.check(R.id.radio_button_make_own_malware)
            }
        }
    }

    private fun disableUnnecessarySecurityRadioButtons() {
        val challenge: Challenge = ChallengeViewModel.instance.challenge

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

    private fun getMode(): OperationMode {
        val operationModeID: Int = findViewById<RadioGroup>(R.id.operationModeRadioGroup).checkedRadioButtonId

        return when (operationModeID) {
            R.id.radio_button_beginner -> OperationMode.BEGINNER
            R.id.radio_button_experienced -> OperationMode.EXPERIENCED
            R.id.radio_button_make_own_malware -> OperationMode.MAKE_OWN_MALWARE
            else -> throw IllegalStateException("invalid operation mode selection!")
        }
    }

    fun onApplySettings(view: View) {
        applySecuritySettings()
        if (intent.extras != null) {
            val launchedFromChallengeActivity: Boolean = intent.extras!!.get(
                    "launchedFromChallengeActivity") as Boolean
            val operationMode: OperationMode = getMode()

            if (!launchedFromChallengeActivity) {
                if (operationMode == OperationMode.MAKE_OWN_MALWARE) {
                    val intent = Intent(this, MakeOwnMalwareChallengeActivity::class.java)
                    intent.putExtra("mode", operationMode)
                    startActivity(intent)
                } else {
                    val intent = Intent(this, ChallengeActivity::class.java)
                    intent.putExtra("mode", operationMode)
                    startActivity(intent)
                }
            } else {
                val intent = Intent()
                intent.putExtra("mode", operationMode)
                setResult(Activity.RESULT_OK, intent)
            }
        }

        finish()
    }

    private fun applySecuritySettings() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.
                WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            val storageState = Environment.getExternalStorageState()

            if ((storageState == Environment.MEDIA_MOUNTED)) {
                saveSecuritySettingsToFile()
            }
        } else {
            writeRequestStoragePermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    private fun saveSecuritySettingsToFile() {
        val settingsFile = File(this.getExternalFilesDir(null), "dvmicc.txt")
        val writer = OutputStreamWriter(FileOutputStream(settingsFile))

        val challengeIndex: Int = ChallengeViewModel.instance.challenge.challengeNameIndex

        val settings: String = "%d\n%s\n%b".format(challengeIndex, getSecurityLevelSelection(),
                getMalwareEnabledSelection())
        writer.write(settings)

        writer.close()
    }

    private fun getSecurityLevelSelection(): String {
        val vulnerableSecurityLevelButtonGroup = findViewById<RadioGroup>(R.id
                .vulnerableAppSecurityLevelRadioGroup)

        return findViewById<RadioButton>(vulnerableSecurityLevelButtonGroup.checkedRadioButtonId)
                .text.toString().toLowerCase(Locale.ROOT)
    }

    private fun getMalwareEnabledSelection(): Boolean {
        return findViewById<SwitchMaterial>(R.id.malwareEnabledSwitch).isChecked
    }

    private fun initialiseSecuritySettings() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {
            val storageState = Environment.getExternalStorageState()

            if ((storageState == Environment.MEDIA_MOUNTED) || (storageState == Environment.MEDIA_MOUNTED_READ_ONLY)) {
                loadSecuritySettingsFromFile()
            }
        } else {
            readRequestStoragePermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }

    private fun loadSecuritySettingsFromFile() {
        val settingsFile = File(this.getExternalFilesDir(null), "dvmicc.txt")

        if (settingsFile.exists()) {
            val reader = BufferedReader(InputStreamReader(FileInputStream(settingsFile)))

            reader.readLine()
            val vulnerableAppSecurityLevel = reader.readLine().toLowerCase(Locale.ROOT)
            val malwareEnabled = reader.readLine().toLowerCase(Locale.ROOT)
            reader.close()

            val vulnerableSecurityLevelButtonGroup = findViewById<RadioGroup>(
                    R.id.vulnerableAppSecurityLevelRadioGroup)

            when (vulnerableAppSecurityLevel) {
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
                }
                else -> {
                    throw IllegalStateException("Security level in file has invalid value!")
                }
            }

            val malwareEnabledSwitch = findViewById<SwitchCompat>(R.id.malwareEnabledSwitch)
            malwareEnabledSwitch.isChecked = malwareEnabled.toBoolean()
        }
    }
}
