package tn.superhich.covid19watcher.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import tn.superhich.covid19watcher.data.model.CountryData
import tn.superhich.covid19watcher.data.model.GlobalInfo

interface Services {

    companion object {
        const val BASE_PATH = "free-api?"
    }

    @GET("${BASE_PATH}global=stats")
    fun getGlobalInfo() : Call<GlobalInfo>

    @GET(BASE_PATH)
    fun getCountryTotal(@Query("countryTotal") countryCode: String) : Call<CountryData>

    @GET(BASE_PATH)
    fun getCountryTotals(@Query("countryTotals") countryCode: String) : Call<String>
}