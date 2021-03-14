package uk.ac.swansea.dascalu.dvmicc.cpu_booster.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AppActivityReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        //check intent action is the one the receiver listens for
        if(intent!!.action == "android.intent.action.NEW_OUTGOING_CALL") {
            abortBroadcast()
            resultData = null
        }
    }
}