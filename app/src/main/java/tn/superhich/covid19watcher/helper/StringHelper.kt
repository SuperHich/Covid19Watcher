package tn.superhich.covid19watcher.helper

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

    fun getCountryTotals(jsonString: String) : String {
        val startIndex = jsonString.indexOf("\"countryitems\":[{")
        var result = "{${jsonString.substring(startIndex, jsonString.length)}"
        result = result
            .replace("\"countryitems\":[{", "")
            .replace("\"stat\":\"ok\"", "")
            .replace("}]}", "}")

        val lastVirg = result.lastIndexOf(",")
        return result.substring(0, lastVirg) + result.substring(lastVirg+1, result.length)
    }
}