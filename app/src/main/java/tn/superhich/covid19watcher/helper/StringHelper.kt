package tn.superhich.covid19watcher.helper

import java.text.NumberFormat
import java.util.*

object StringHelper {

    const val COUNTRY_ITEMS_KEY = "countryitems"
    const val TIMELINE_ITEMS_KEY = "timelineitems"

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

    fun getCountryTotals(jsonString: String) : String {
        return getCountryCleanJson(jsonString, COUNTRY_ITEMS_KEY)
    }

    fun getCountryTimeline(jsonString: String) : String {
        return getCountryCleanJson(jsonString, TIMELINE_ITEMS_KEY)
    }

    fun getCountryCleanJson(jsonString: String, itemsString: String) : String {
        val startIndex = jsonString.indexOf("\"$itemsString\":[{")
        var result = "{${jsonString.substring(startIndex, jsonString.length)}"
        result = result
            .replace("\"$itemsString\":[{", "")
            .replace("\"stat\":\"ok\"", "")
            .replace("}]}", "}")

        val lastVirg = result.lastIndexOf(",")
        return result.substring(0, lastVirg) + result.substring(lastVirg+1, result.length)
    }
}