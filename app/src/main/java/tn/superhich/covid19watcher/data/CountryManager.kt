package tn.superhich.covid19watcher.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tn.superhich.covid19watcher.data.model.LocalCountry
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
}