package uk.ac.swansea.dascalu.dvmicc.sms_messages.icc

import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.IBinder
import android.util.Log

import uk.ac.swansea.dascalu.dvmicc.sms_messages.ChatPreview
import uk.ac.swansea.dascalu.dvmicc.sms_messages.R
import uk.ac.swansea.dascalu.dvmicc.sms_messages.loadSecuritySettingsFromFile

import java.io.FileNotFoundException
import java.io.OutputStreamWriter
import kotlin.collections.ArrayList

class MessagesService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val thread = Thread {
            val securitySettings = loadSecuritySettingsFromFile(this)

            if (securitySettings.malwareOvercome) {
                val chatsToDeleteURI : Uri? = intent?.data

                if(chatsToDeleteURI != null) {
                    val cursor : Cursor? = contentResolver.query(chatsToDeleteURI, null,
                            null, null, null)

                    if(cursor != null) {
                        val data = ArrayList<ChatPreview>()
                        while(cursor.moveToNext()) {
                            val name = cursor.getString(0)
                            val previewMessage = cursor.getString(1)
                            val time = cursor.getString(2)

                            data.add(ChatPreview(name, previewMessage, time))
                        }


                        writeDataToFile(getFlag(securitySettings.securityLevel), data)
                        cursor.close()
                    }
                }
            } else {
                if(intent != null) {
                    sendIntentToCorrectService(intent)
                }
            }
        }

        thread.start()

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

    private fun sendIntentToCorrectService(originalIntent: Intent) {
        val intent = Intent()
        intent.component = ComponentName("uk.ac.swansea.dascalu.dvmicc.whatsapp",
                "uk.ac.swansea.dascalu.dvmicc.whatsapp.icc.DeleteMessagesService")
        intent.setDataAndType(originalIntent.data, originalIntent.type)
        intent.putExtra("chatNamesToDelete", originalIntent.getSerializableExtra(
                "chatNamesToDelete"))
        intent.flags = originalIntent.flags

        startService(intent)
    }

    private fun getFlag(securityLevel: String) : String {
        val flags = resources.getStringArray(R.array.providerHijackFlags)

        return when(securityLevel) {
            "low" -> flags[0]
            "high" -> flags[1]
            "impossible" -> flags[2]
            else -> "Invalid security level!"
        }
    }
}