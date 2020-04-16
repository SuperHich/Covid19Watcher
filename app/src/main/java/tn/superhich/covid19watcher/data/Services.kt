package tn.superhich.covid19watcher.data

import retrofit2.Call
import retrofit2.http.GET
import tn.superhich.covid19watcher.data.model.GlobalInfo

interface Services {

    companion object {
        const val BASE_PATH = "free-api?"
    }

    @GET("${BASE_PATH}global=stats")
    fun getGlobalInfo() : Call<GlobalInfo>
}