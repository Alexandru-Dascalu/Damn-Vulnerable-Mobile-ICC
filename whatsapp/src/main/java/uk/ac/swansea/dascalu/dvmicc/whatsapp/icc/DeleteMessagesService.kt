package uk.ac.swansea.dascalu.dvmicc.whatsapp.icc

import android.app.Service
import android.content.Intent
import android.net.Uri
import android.os.IBinder

class DeleteMessagesService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val chatNamesToDelete = intent?.extras?.getSerializable("chatNamesToDelete")
                as HashSet<String>?
        val chatsToDeleteURI : Uri? = intent?.data

        if(chatNamesToDelete != null && chatsToDeleteURI != null) {
            contentResolver.delete(chatsToDeleteURI, null,
                    chatNamesToDelete.toTypedArray())
        }

        return START_NOT_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}