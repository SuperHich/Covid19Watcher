package tn.superhich.covid19watcher.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    companion object {
        //private val BASE_URL = "https://covidapi.info/api/v1/"
        private val BASE_URL = "https://thevirustracker.com/"
        private var retrofit: Retrofit? = null

        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }

}