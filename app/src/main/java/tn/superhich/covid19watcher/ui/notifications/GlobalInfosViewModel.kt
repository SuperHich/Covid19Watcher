package tn.superhich.covid19watcher.ui.notifications

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.superhich.covid19watcher.data.CountryManager
import tn.superhich.covid19watcher.data.RetrofitClient
import tn.superhich.covid19watcher.data.Services
import tn.superhich.covid19watcher.data.model.CountryTotalItem
import tn.superhich.covid19watcher.data.model.GlobalInfo
import tn.superhich.covid19watcher.data.model.LocalCountry
import tn.superhich.covid19watcher.data.model.TotalInfo

class GlobalInfosViewModel(application: Application) : AndroidViewModel(application) {

    private val _totalInfo = MutableLiveData<TotalInfo>().apply {
        value = this.value
    }
    val totalInfo : LiveData<TotalInfo> = _totalInfo

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _countryList = MutableLiveData<List<LocalCountry>>().apply {
        value = CountryManager().getCountryList(getApplication())
    }

    private val _countryTotalsList = MutableLiveData<List<CountryTotalItem>>()
    val countryTotalsList: LiveData<List<CountryTotalItem>> = _countryTotalsList



    fun loadCountryTotalslInfo() {
        val service = RetrofitClient.getRetrofitInstance()?.create(Services::class.java)
        val call = service?.getCountryTotals()
        call?.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                val countryTotals = CountryManager().getCountryTotals(response.body() ?: "").values.toList()
                _countryTotalsList.value = countryTotals
                Log.d("=====", "size ${countryTotals.size}")
            }
            override fun onFailure(call: Call<String>, t: Throwable) {
            }
        })
    }

    fun updateListToShow(listToShow: ArrayList<String>) {
        _countryTotalsList.value = _countryTotalsList.value?.filter { country ->
            country.code != "all" && listToShow.any { it == country.title.toLowerCase() }
        }
    }


    fun loadGlobalInfo() {
        val service = RetrofitClient.getRetrofitInstance()?.create(Services::class.java)
        val call = service?.getGlobalInfo()
        call?.enqueue(object : Callback<GlobalInfo> {
            override fun onResponse(call: Call<GlobalInfo>, response: Response<GlobalInfo>) {
                response.body()?.results?.first()?.let {
                    _totalInfo.value = it
                }
            }

            override fun onFailure(call: Call<GlobalInfo>, t: Throwable) {
            }
        })
    }
}