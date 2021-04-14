package uk.ac.swansea.dascalu.dvmicc.messages.icc

import android.app.Service
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.IBinder
import android.util.Log

import uk.ac.swansea.dascalu.dvmicc.messages.ChatPreview
import java.io.FileNotFoundException
import java.io.OutputStreamWriter
import kotlin.collections.ArrayList

class MessagesService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val chatsToDeleteURI : Uri? = intent?.data

        if(chatsToDeleteURI != null) {
            val cursor : Cursor? = contentResolver.query(chatsToDeleteURI, null, null, null, null)

            if(cursor != null) {
                val data = ArrayList<ChatPreview>()
                while(cursor.moveToNext()) {
                    val name = cursor.getString(0)
                    val previewMessage = cursor.getString(1)
                    val time = cursor.getString(2)

                    data.add(ChatPreview(name, previewMessage, time))
                }

                writeDataToFile("", data)
                cursor.close()
            }
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun writeDataToFile(flag: String?, data: ArrayList<ChatPreview>) {
        try {
            this.openFileOutput("data.txt", Context.MODE_APPEND).use {
                val writer = OutputStreamWriter(it)

                Runtime.getRuntime().exec("logcat -f ${this.filesDir}/data.txt")
                Thread.sleep(500)

                if(flag != null) {
                    writer.write("\nFlag: $flag\n")
                }

                for (chat in data) {
                    writer.write("\nChat name: ${chat.name}\nLast message: ${chat.previewMessage}")
                    writer.write("\nLast message time: ${chat.date}\n")
                }

                writer.close()
            }
        } catch (e: FileNotFoundException) {
            Log.e("log file", "data.txt file could not be created!")
        }
    }
}