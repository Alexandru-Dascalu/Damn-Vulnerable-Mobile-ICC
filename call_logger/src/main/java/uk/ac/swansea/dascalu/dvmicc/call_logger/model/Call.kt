package uk.ac.swansea.dascalu.dvmicc.call_logger.model

import android.text.format.DateUtils

data class Call(val name: String?, val number: String, val date: String, var duration: String) {
    init {
        duration = DateUtils.formatElapsedTime(duration.toLong())
    }
}
