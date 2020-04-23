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
import tn.superhich.covid19watcher.helper.DateHelper
import java.util.*

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        const val UNKNOWN_ERROR = "UNKNOWN_ERROR"
    }

    val error = MutableLiveData<String>().apply {
        value = this.value
    }

    private val _totalInfo = MutableLiveData<TotalInfo>().apply {
        value = this.value
    }
    val totalInfo : LiveData<TotalInfo> = _totalInfo

    private val _todayTotalInfo = MutableLiveData<TotalInfo>().apply {
        value = this.value
    }
    val todayTotalInfo : LiveData<TotalInfo> = _todayTotalInfo

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

    var lastGlobalCall: Calendar? = null

    fun loadGlobalInfo(today: Boolean) {
        val isGlobalCallEligible = DateHelper.isGlobalCallEligible(lastGlobalCall)
        if(isGlobalCallEligible) {
            val service = RetrofitClient.getRetrofitInstance()?.create(Services::class.java)
            val call = service?.getGlobalInfo()
            call?.enqueue(object : Callback<GlobalInfo> {
                override fun onResponse(call: Call<GlobalInfo>, response: Response<GlobalInfo>) {
                    response.body()?.results?.first()?.let {
                        lastGlobalCall = Calendar.getInstance()
                        postGlobalInfo(today, it)
                    }
                }

                override fun onFailure(call: Call<GlobalInfo>, t: Throwable) {
                    error.value = UNKNOWN_ERROR
                }
            })

        } else {
            postGlobalInfo(today)
        }
    }

    fun postGlobalInfo(today: Boolean, info: TotalInfo? = null) {
        if(today) {
            _totalInfo.value = info ?: totalInfo.value
            _todayTotalInfo.value = info ?: todayTotalInfo.value
        } else {
            _todayTotalInfo.value = info ?: todayTotalInfo.value
            _totalInfo.value = info ?: totalInfo.value
        }
    }

    fun loadCountryTotal(countryCode: String?, today: Boolean) {
        if(countryCode != null) {
            val service = RetrofitClient.getRetrofitInstance()?.create(Services::class.java)
            val call = service?.getCountryTotal(countryCode)
            call?.enqueue(object : Callback<CountryData> {
                override fun onResponse(call: Call<CountryData>, response: Response<CountryData>) {
                    response.body()?.countrydata?.first()?.let {
                        if(today) {
                            todayCountryDataItem.value = it
                        } else {
                            countryDataItem.value = it
                        }
                    }
                }

                override fun onFailure(call: Call<CountryData>, t: Throwable) {
                    error.value = UNKNOWN_ERROR
                }
            })
        } else {
            error.value = UNKNOWN_ERROR
        }
    }
}