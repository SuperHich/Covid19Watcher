package tn.superhich.covid19watcher.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.superhich.covid19watcher.data.CountryManager
import tn.superhich.covid19watcher.data.RetrofitClient
import tn.superhich.covid19watcher.data.Services
import tn.superhich.covid19watcher.data.model.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    val error = MutableLiveData<String>().apply {
        value = this.value
    }

    val totalInfo = MutableLiveData<TotalInfo>().apply {
        value = this.value
    }

    val todayTotalInfo = MutableLiveData<TotalInfo>().apply {
        value = this.value
    }

    val countryDataItem = MutableLiveData<CountryDataItem>().apply {
        value = this.value
    }

    val todayCountryDataItem = MutableLiveData<CountryDataItem>().apply {
        value = this.value
    }

    private val _countryList = MutableLiveData<List<LocalCountry>>().apply {
        value = CountryManager().getCountryList(getApplication())
    }
    val localCountryList: LiveData<List<LocalCountry>> = _countryList

    fun loadGlobalInfo(countryCode: String?, today: Boolean) {
        if(countryCode != null) {
            val service = RetrofitClient.getRetrofitInstance()?.create(Services::class.java)
            val call = service?.getGlobalInfo()
            call?.enqueue(object : Callback<GlobalInfo> {
                override fun onResponse(call: Call<GlobalInfo>, response: Response<GlobalInfo>) {
                    response.body()?.results?.first()?.let {
                        if (today) {
                            todayTotalInfo.value = it
                        } else {
                            totalInfo.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<GlobalInfo>, t: Throwable) {
                    error.value = "Something went wrong...Please try later!"
                }
            })
        } else {
            error.value = "Something went wrong...Please try later!"
        }
    }

    fun loadCountryTotal(countryCode: String?, today: Boolean) {
        if(countryCode != null) {
            val service = RetrofitClient.getRetrofitInstance()?.create(Services::class.java)
            val call = service?.getCountryTotal(countryCode)
            call?.enqueue(object : Callback<CountryData> {
                override fun onResponse(call: Call<CountryData>, response: Response<CountryData>) {
                    response.body()?.countrytimelinedata?.first()?.let {
                        if(today) {
                            todayCountryDataItem.value = it
                        } else {
                            countryDataItem.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<CountryData>, t: Throwable) {
                    error.value = "Something went wrong...Please try later!"
                }
            })
        } else {
            error.value = "Something went wrong...Please try later!"
        }
    }
}