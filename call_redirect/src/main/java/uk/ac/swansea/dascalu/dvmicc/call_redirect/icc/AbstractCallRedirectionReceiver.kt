package uk.ac.swansea.dascalu.dvmicc.call_redirect.icc

import android.content.BroadcastReceiver
import android.content.Context
import uk.ac.swansea.dascalu.dvmicc.call_redirect.BROADCAST_THEFT_DOS_ID
import uk.ac.swansea.dascalu.dvmicc.call_redirect.BROADCAST_THEFT_MITM_ID

abstract class AbstractCallRedirectionReceiver : BroadcastReceiver() {
    protected fun addCountryCode(phoneNumber: String, countryCode: String?) : String {
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

    protected fun getCountryCode(context: Context?) : String? {
        if(context != null) {
            val sharedPreferences = context.getSharedPreferences("country_code",
                Context.MODE_PRIVATE)
            return sharedPreferences.getString("country_code", null)
        } else {
            return null
        }
    }

    protected fun isCurrentChallengeCorrect(currentChallengeIndex : Int?) : Boolean {
        return currentChallengeIndex == BROADCAST_THEFT_DOS_ID ||
                currentChallengeIndex == BROADCAST_THEFT_MITM_ID
    }
}