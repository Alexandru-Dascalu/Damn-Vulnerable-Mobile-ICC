package uk.ac.swansea.dascalu.newsaggregator.icc

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NewsService : Service() {

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}