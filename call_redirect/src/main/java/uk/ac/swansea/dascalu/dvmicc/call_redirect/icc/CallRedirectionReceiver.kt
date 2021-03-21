package uk.ac.swansea.dascalu.dvmicc.call_redirect.icc

import android.content.Context
import android.content.Intent
import uk.ac.swansea.dascalu.dvmicc.call_redirect.BROADCAST_THEFT_DOS_ID

import uk.ac.swansea.dascalu.dvmicc.call_redirect.SecuritySettings
import uk.ac.swansea.dascalu.dvmicc.call_redirect.loadSecuritySettingsFromFile


class CallRedirectionReceiver : AbstractCallRedirectionReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val securityLevelSettings : SecuritySettings = loadSecuritySettingsFromFile(context!!)

        /*Only do sth if the broadcast theft dos challenge is active and if level is not high.*/
        if(securityLevelSettings.currentChallengeIndex == BROADCAST_THEFT_DOS_ID &&
                (securityLevelSettings.securityLevel == "low" || securityLevelSettings.securityLevel
                        == "impossible")) {
            //check intent action is the one the receiver listens for
            if(intent!!.action == "android.intent.action.NEW_OUTGOING_CALL") {
                var phoneNumber : String? = resultData

                if(phoneNumber == null) {
                    phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
                }

                //check if number does not already have country code
                if(phoneNumber!![0] != '+') {
                    resultData = addCountryCode(phoneNumber, getCountryCode(context))
                }
            }
        }
    }
 }