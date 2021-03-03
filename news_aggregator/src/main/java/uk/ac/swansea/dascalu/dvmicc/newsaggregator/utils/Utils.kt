package uk.ac.swansea.dascalu.dvmicc.newsaggregator.utils

import android.content.Context
import android.os.Environment
import android.text.format.DateUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import uk.ac.swansea.dascalu.dvmicc.newsaggregator.R
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Calendar
import java.util.Locale

fun hideKeyboard(currentView: View, context: Context) {
    val keyboardManager = context.getSystemService(Context.INPUT_METHOD_SERVICE)
            as InputMethodManager
    keyboardManager.hideSoftInputFromWindow(currentView.windowToken, 0)
}

/**
 * Gets a nicely string describing when the article was published. If it was published less
 * than 7 days ago, the string will be 'x days ago' or 'x minutes ago'. It might be in another
 * language, depending on your Locale. If the article is more than 7 days old, it will display
 * the date, such 24 nov, 2020 .
 * @param publishingTime The publishing time of the article, in the yyyy-MM-dd'T'HH:mm:ss'Z' format.
 * @return a string representing the publishing time relative to the current time.
 */
fun getPublishTimeAgo(publishingTime: String) : String {
    val utcDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val publishTimeDate: Date = utcDateFormat.parse(publishingTime)!!

    val timeAgo: String = DateUtils.getRelativeTimeSpanString(
        publishTimeDate.time,
        Calendar.getInstance().timeInMillis,
        DateUtils.MINUTE_IN_MILLIS
    ).toString()

    return timeAgo
}

fun loadSecuritySettingsFromFile(context: Context) : String {
    val storageState = Environment.getExternalStorageState()

    if((storageState == Environment.MEDIA_MOUNTED) || storageState == Environment.MEDIA_MOUNTED_READ_ONLY) {
        val homeAppContext = context.createPackageContext("uk.ac.swansea.dascalu.dvmicc.home", Context.CONTEXT_IGNORE_SECURITY)
        val settingsFile = File(homeAppContext.getExternalFilesDir(null), "dvmicc.txt")

        if (settingsFile.exists()) {
            val reader = BufferedReader(InputStreamReader(FileInputStream(settingsFile)))

            val vulnerableAppSecurityLevel = reader.readLine().toLowerCase(Locale.ROOT)

            reader.close()
            return vulnerableAppSecurityLevel
        } else {
            Toast.makeText(context, "Security level settings file is not present!",
                    Toast.LENGTH_LONG).show()

            return context.getString(R.string.lowSecurityLevel)
        }
    } else {
        Toast.makeText(context, "External storage is not present!",
                Toast.LENGTH_LONG).show()

        return context.getString(R.string.lowSecurityLevel)
    }
}