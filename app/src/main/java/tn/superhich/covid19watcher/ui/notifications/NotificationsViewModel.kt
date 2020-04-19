package tn.superhich.covid19watcher.ui.notifications

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import tn.superhich.covid19watcher.data.CountryManager
import tn.superhich.covid19watcher.data.model.LocalCountry

class NotificationsViewModel(application: Application) : AndroidViewModel(application) {

    private val _text = MutableLiveData<String>().apply {
        value = "This is notifications Fragment"
    }
    val text: LiveData<String> = _text

    private val _countryList = MutableLiveData<List<LocalCountry>>().apply {
        value = CountryManager().getCountryList(getApplication())
    }
    val localCountryList: LiveData<List<LocalCountry>> = _countryList
}