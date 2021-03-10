package uk.ac.swansea.dascalu.dvmicc.call_logger

import android.content.Context
import android.os.Environment
import android.widget.Toast
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Locale

import uk.ac.swansea.dascalu.dvmicc.call_logger.model.SecuritySetting

fun loadSecuritySettingsFromFile(context: Context) : SecuritySetting? {
    val storageState = Environment.getExternalStorageState()

    if((storageState == Environment.MEDIA_MOUNTED) || storageState == Environment.MEDIA_MOUNTED_READ_ONLY) {
        val homeAppContext = context.createPackageContext(
                "uk.ac.swansea.dascalu.dvmicc.home", Context.CONTEXT_IGNORE_SECURITY)
        val settingsFile = File(homeAppContext.getExternalFilesDir(null), "dvmicc.txt")

        if(settingsFile.exists()) {
            val reader = BufferedReader(InputStreamReader(FileInputStream(settingsFile)))
            val vulnerableAppSecurityLevel = reader.readLine().toLowerCase(Locale.ROOT)
            val malwareSecurityLevel: Boolean = reader.readLine().toBoolean()

            reader.close()
            return SecuritySetting(vulnerableAppSecurityLevel, malwareSecurityLevel)
        } else {
            Toast.makeText(context, "Security level settings file is not present!",
                    Toast.LENGTH_LONG).show()

            return null
        }
    } else {
        Toast.makeText(context, "External storage is not present!",
                Toast.LENGTH_LONG).show()

        return null
    }
}