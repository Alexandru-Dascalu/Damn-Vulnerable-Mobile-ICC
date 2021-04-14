package uk.ac.swansea.dascalu.dvmicc.messages

import android.content.Context
import android.os.Environment
import android.widget.Toast

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Locale

data class SecuritySettings(val currentChallengeIndex: Int?, val securityLevel: String,
                            val malwareOvercome: Boolean)

fun loadSecuritySettingsFromFile(context: Context) : SecuritySettings {
    val storageState = Environment.getExternalStorageState()

    if(storageState == Environment.MEDIA_MOUNTED || storageState == Environment.MEDIA_MOUNTED_READ_ONLY) {
        val homeAppContext = context.createPackageContext("uk.ac.swansea.dascalu.dvmicc.home",
                Context.CONTEXT_IGNORE_SECURITY)
        val settingsFile = File(homeAppContext.getExternalFilesDir(null), "dvmicc.txt")

        if (settingsFile.exists()) {
            val reader = BufferedReader(InputStreamReader(FileInputStream(settingsFile)))
            reader.use {
                val currentChallengeIndex : Int? = reader.readLine().toIntOrNull()
                val vulnerableAppSecurityLevel = reader.readLine().toLowerCase(Locale.ROOT)
                val malwareOvercome : Boolean = reader.readLine().toBoolean()

                return SecuritySettings(currentChallengeIndex, vulnerableAppSecurityLevel,
                        malwareOvercome)
            }
        } else {
            Toast.makeText(context, "Security level settings file is not present!",
                    Toast.LENGTH_LONG).show()

            return SecuritySettings(null, "low", false)
        }
    } else {
        Toast.makeText(context, "External storage is not present!",
                Toast.LENGTH_LONG).show()

        return SecuritySettings(null, "low", false)
    }
}