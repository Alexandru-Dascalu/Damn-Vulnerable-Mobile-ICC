package uk.ac.swansea.dascalu.dvmicc.call_redirect.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.TelephonyManager

import uk.ac.swansea.dascalu.dvmicc.call_redirect.loadSecuritySettingsFromFile
import kotlin.concurrent.thread


class CallRedirectionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val securityLevel = loadSecuritySettingsFromFile(context!!)

        //this receiver is only for the low level
        if(securityLevel == "low") {
            //it will also get a broadcast for action PHONE_STATE, which it ignores for level low
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
        } else if(securityLevel == "medium") {
            //it will also get a broadcast for action PHONE_STATE, which it ignores for level low
            //check intent action is the one the receiver listens for
            if (intent!!.action == "android.intent.action.NEW_OUTGOING_CALL") {
                var phoneNumber: String? = resultData

                if (phoneNumber == null) {
                    phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
                }

                //check if number does not already have country code
                if (phoneNumber!![0] != '+') {
                    resultData = addCountryCode(phoneNumber, getCountryCode(context))
                    abortBroadcast()
                }
            }
        } else if(securityLevel == "impossible") {
            if(intent!!.action == "android.intent.action.PHONE_STATE") {
                var phoneNumber : String? = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER)
                //check if number does not already have country code
                if(phoneNumber!![0] != '+') {
                    phoneNumber = addCountryCode(phoneNumber, getCountryCode(context))
                }

                //stole this from https://stackoverflow.com/a/51447053
//                val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
//                val m1 = telephonyManager::class.java.getDeclaredMethod("getITelephony")
//                m1.isAccessible = true
//                val telephonyService = m1.invoke(telephonyManager)
//                val m2 = telephonyService::class.java.getDeclaredMethod("silenceRinger")
//                val m3 = telephonyService::class.java.getDeclaredMethod("endCall")
//
//                m2.invoke(telephonyService)
//                m3.invoke(telephonyService)

                val newCallIntent = Intent(Intent.ACTION_DIAL)
                newCallIntent.data = Uri.parse("tel:$phoneNumber")

                context.startActivity(newCallIntent)
            }
        }
    }

    private fun addCountryCode(phoneNumber: String, countryCode: String?) : String {
        var formattedPhoneNumber : String = phoneNumber

        //check if we need to add a country code
        if(countryCode != null || countryCode != "") {
            //check if we should strip initial 0 from number
            if(phoneNumber.get(0) == '0') {
                formattedPhoneNumber = phoneNumber.drop(1)
            }

            formattedPhoneNumber = "+$countryCode$formattedPhoneNumber"
        }
        return formattedPhoneNumber
    }

    private fun getCountryCode(context: Context?) : String? {
        if(context != null) {
            val sharedPreferences = context.getSharedPreferences("country_code",
                Context.MODE_PRIVATE)
            return sharedPreferences.getString("country_code", null)
        } else {
            return null
        }
    }
 }