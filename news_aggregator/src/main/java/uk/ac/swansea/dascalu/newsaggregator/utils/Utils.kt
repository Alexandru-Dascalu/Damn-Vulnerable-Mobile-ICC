package uk.ac.swansea.dascalu.newsaggregator.utils

import android.content.Context
import android.text.format.DateUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.*

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