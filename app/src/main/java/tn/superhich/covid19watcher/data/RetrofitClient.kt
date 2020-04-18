package tn.superhich.covid19watcher.data

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import tn.superhich.covid19watcher.BuildConfig
import java.util.concurrent.TimeUnit

class RetrofitClient {

    companion object {
        //private val BASE_URL = "https://covidapi.info/api/v1/"
        private val BASE_URL = "https://api.thevirustracker.com/"
        private var retrofit: Retrofit? = null

        fun getRetrofitInstance(): Retrofit? {
            if (retrofit == null) {

                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor(makeLoggingInterceptor())
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    //.addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(makeJacksonConverter())
                    .build()
            }
            return retrofit
        }

        private fun makeLoggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = if (BuildConfig.DEBUG)
                HttpLoggingInterceptor.Level.BODY
            else
                HttpLoggingInterceptor.Level.NONE
            return logging
        }

        private fun makeJacksonConverter(): JacksonConverterFactory {
            val objectMapper = ObjectMapper()
            objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            objectMapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
            return JacksonConverterFactory.create(objectMapper)
        }
    }

}