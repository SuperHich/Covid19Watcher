package tn.superhich.covid19watcher.helper

import java.util.*

object DateHelper {

    private const val PERIOD_CALL_IN_MILLIS = 1000 * 60 // authorize call every 1 min minimum

    fun isGlobalCallEligible(lastCall: Calendar) : Boolean {
        val now = Calendar.getInstance()
        return now.timeInMillis - lastCall.timeInMillis >= PERIOD_CALL_IN_MILLIS
    }
}