package uk.ac.swansea.dascalu.dvmicc.santander

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

const val LOGIN_ACTION = "uk.ac.swansea.dascalu.dvmicc.santander.intent.action.LOGIN"
var EVERYDAY_ACCOUNT_BALANCE : Int = 4628

fun hideKeyboard(currentView: View, context: Context) {
    val keyboardManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    keyboardManager.hideSoftInputFromWindow(currentView.windowToken, 0)
}
