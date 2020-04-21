package tn.superhich.covid19watcher.helper

import android.content.Context
import android.preference.PreferenceManager
import tn.superhich.covid19watcher.data.model.LocalCountry

class SharePrefs(val context: Context?) {

    companion object {
        const val MY_LOCAL_COUNTRY_CODE = "MY_LOCAL_COUNTRY_CODE"
        const val MY_LOCAL_COUNTRY_NAME = "MY_LOCAL_COUNTRY_NAME"
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
}