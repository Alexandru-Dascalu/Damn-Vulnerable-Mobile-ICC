package uk.ac.swansea.dascalu.dvmicc.swan_bank

import android.content.Context
import android.os.Environment
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Locale

const val LOGIN_ACTION = "uk.ac.swansea.dascalu.dvmicc.swan_bank.intent.action.LOGIN"
var EVERYDAY_ACCOUNT_BALANCE : Int = 4628

const val ACTIVITY_INTENT_HIJACK : Int = 3

fun loadSecuritySettingsFromFile(context: Context) : String {
    val storageState = Environment.getExternalStorageState()

    if(storageState == Environment.MEDIA_MOUNTED || storageState == Environment.MEDIA_MOUNTED_READ_ONLY) {
        val homeAppContext = context.createPackageContext("uk.ac.swansea.dascalu.dvmicc.home",
                Context.CONTEXT_IGNORE_SECURITY)
        val settingsFile = File(homeAppContext.getExternalFilesDir(null), "dvmicc.txt")

        if (settingsFile.exists()) {
            val reader = BufferedReader(InputStreamReader(FileInputStream(settingsFile)))
            reader.use {
                reader.readLine()
                val vulnerableAppSecurityLevel = reader.readLine().toLowerCase(Locale.ROOT)

                return vulnerableAppSecurityLevel
            }
        } else {
            Toast.makeText(context, "Security level settings file is not present!",
                    Toast.LENGTH_LONG).show()

            return "low"
        }
    } else {
        Toast.makeText(context, "External storage is not present!",
                Toast.LENGTH_LONG).show()

        return "low"
    }
}

fun hideKeyboard(currentView: View, context: Context) {
    val keyboardManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    keyboardManager.hideSoftInputFromWindow(currentView.windowToken, 0)
}
