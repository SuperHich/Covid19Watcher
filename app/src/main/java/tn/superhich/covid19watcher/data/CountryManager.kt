package tn.superhich.covid19watcher.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tn.superhich.covid19watcher.data.model.CountryTotalItem
import tn.superhich.covid19watcher.data.model.LocalCountry
import tn.superhich.covid19watcher.helper.StringHelper
import java.io.IOException

class CountryManager {

    fun getCountryList(context: Context): List<LocalCountry> = run {
        val countryJson = getCountryJson(context)
        val listPersonType = object : TypeToken<List<LocalCountry>>() {}.type
        return Gson().fromJson(countryJson, listPersonType)
    }

    fun getCountryDrawableId(countryCode: String, context: Context) =
        context.resources.getIdentifier(countryCode, "drawable", context.packageName)

    private fun getCountryJson(context: Context): String? {
        val jsonString: String
        try {
            jsonString = context.assets.open("country.json").bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }
        return jsonString
    }


    fun getCountryTotals(jsonString: String) = run {
        val cleanJsonResponse = StringHelper.getCountryTotals(jsonString)
        parseCountryTotals(cleanJsonResponse) ?: emptyMap()
    }

    private fun parseCountryTotals(jsonString: String): Map<Int, CountryTotalItem>? = run {
        return try {
            val type = object : TypeToken<Map<Int, CountryTotalItem>>() {}.type
            Gson().fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}