package tn.superhich.covid19watcher.helper

import java.lang.Exception
import java.text.NumberFormat
import java.util.*

object StringHelper {

    fun formatNumber(value: Int?) : String? {
        return value?.let {
            try {
                NumberFormat.getInstance(Locale.getDefault()).format(value)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }
}