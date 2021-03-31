package uk.ac.swansea.dascalu.dvmicc.call_redirect.icc

import android.content.Context
import android.content.Intent

import uk.ac.swansea.dascalu.dvmicc.call_redirect.SecuritySettings
import uk.ac.swansea.dascalu.dvmicc.call_redirect.loadSecuritySettingsFromFile


class CallRedirectionReceiver : AbstractCallRedirectionReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val securityLevelSettings : SecuritySettings = loadSecuritySettingsFromFile(context!!)

        /*Only do sth if the current challenge is correct and if level is not high.*/
        if(isCurrentChallengeCorrect(securityLevelSettings.currentChallengeIndex) &&
            isSecurityLevelNotHigh(securityLevelSettings.securityLevel)) {

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

    /*If the broadcast theft DOS or MITM challenges are active, then the only possible security
     * levels are low, high and impossible. If the level is either low or impossible, then it is
     * not high.*/
    private fun isSecurityLevelNotHigh(securityLevel: String) : Boolean {
        return securityLevel == "low" || securityLevel == "impossible"
    }
 }