package tn.superhich.covid19watcher.data

import android.content.Context
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import tn.superhich.covid19watcher.data.model.CountryTimeline
import tn.superhich.covid19watcher.data.model.CountryTotalItem
import tn.superhich.covid19watcher.data.model.LocalCountry
import tn.superhich.covid19watcher.helper.StringHelper
import java.io.IOException
import java.util.*


class CountryManager {

    fun getCountryList(context: Context): List<LocalCountry> = run {
        val countryJson = getCountryJson(context)
        val listPersonType = object : TypeToken<List<LocalCountry>>() {}.type
        return Gson().fromJson(countryJson, listPersonType)
    }

    fun getCountryDrawableId(countryCode: String, context: Context) =
        context.resources.getIdentifier(
            "country_" + countryCode.toLowerCase(),
            "drawable",
            context.packageName
        )

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
//            val type = object : TypeToken<Map<Int, CountryTotalItem>>() {}.type
//            Gson().fromJson(jsonString, type)
            val typeRef: TypeReference<HashMap<Int, CountryTotalItem>> =
                object : TypeReference<HashMap<Int, CountryTotalItem>>() {}
            val sMapper = ObjectMapper()
            sMapper.readValue(jsonString, typeRef)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun getCountryTimeline(jsonString: String) = run {
        val cleanJsonResponse = StringHelper.getCountryTimeline(jsonString)
        parseCountryTimeline(cleanJsonResponse) ?: emptyMap()
    }

    private fun parseCountryTimeline(jsonString: String): Map<Date, CountryTimeline>? = run {
        return try {
            val type = object : TypeToken<Map<Date, CountryTimeline>>() {}.type
            val gson = GsonBuilder()
                .setDateFormat("dd/MM/YY")
                .create()
            gson.fromJson(jsonString, type)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}
