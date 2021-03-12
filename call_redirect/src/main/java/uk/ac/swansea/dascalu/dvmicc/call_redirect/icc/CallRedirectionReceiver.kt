package uk.ac.swansea.dascalu.dvmicc.call_redirect.icc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class CallRedirectionReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        var phoneNumber : String? = resultData

        if(phoneNumber == null) {
            phoneNumber = intent!!.getStringExtra(Intent.EXTRA_PHONE_NUMBER)
        }

        val f = addCountryCode(phoneNumber!!, getCountryCode(context))
        resultData = f
    }

    private fun addCountryCode(phoneNumber: String, countryCode: String?) : String {
        var formattedPhoneNumber : String = phoneNumber

        //check if we need to add a country code
        if(countryCode != null) {
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
            val sharedPreferences = context.getSharedPreferences("country_code", Context.MODE_PRIVATE)
            return sharedPreferences.getString("country_code", null)
        } else {
            return null
        }
    }
 }