package tn.superhich.covid19watcher.helper

import android.content.Context
import android.preference.PreferenceManager
import tn.superhich.covid19watcher.data.model.LocalCountry
import java.util.*

class SharedPrefs(val context: Context?) {

    companion object {
        const val MY_LOCAL_COUNTRY_CODE = "MY_LOCAL_COUNTRY_CODE"
        const val MY_LOCAL_COUNTRY_NAME = "MY_LOCAL_COUNTRY_NAME"
        const val GLOBAL_LAST_CALL = "GLOBAL_LAST_CALL"
    }

    private fun saveToPrefs(key: String, json: String) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putString(key, json)
        editor.apply()
    }

    private fun readFromPrefs(key: String, stringDefault: String?): String? {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getString(key, stringDefault)
    }

    private fun saveToPrefs(key: String, value: Long) {
        val pref = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = pref.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    private fun readFromPrefs(key: String, default: Long): Long {
        val sp = PreferenceManager.getDefaultSharedPreferences(context)
        return sp.getLong(key, default)
    }

    fun saveMyCountry(localCountry: LocalCountry?) {
        localCountry?.let {
            saveToPrefs(MY_LOCAL_COUNTRY_CODE, it.code)
            saveToPrefs(MY_LOCAL_COUNTRY_NAME, it.name)
        }
    }

    fun getMyCountry() : LocalCountry? {
        val code = readFromPrefs(MY_LOCAL_COUNTRY_CODE, "FR")
        val name = readFromPrefs(MY_LOCAL_COUNTRY_NAME, "France")
        return if(code != null && name != null) {
            LocalCountry(code = code, name = name)
        } else
            null
    }

    fun saveGlobalLastCall(calendar: Calendar) {
        saveToPrefs(GLOBAL_LAST_CALL, calendar.timeInMillis)
    }

    fun getGlobalLastCall() : Calendar {
        val value = readFromPrefs(GLOBAL_LAST_CALL, 0)
        return Calendar.getInstance().apply {
            timeInMillis = value
        }
    }


}