package tn.superhich.covid19watcher.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }

    val confirmed = MutableLiveData<String>().apply {
        value = this.value
    }

    val recovered = MutableLiveData<String>().apply {
        value = this.value
    }

    val deaths = MutableLiveData<String>().apply {
        value = this.value
    }

    val text: LiveData<String> = _text
    //val confirmed: LiveData<String> = confirmedLiveData
    //val recovered: LiveData<String> = _recovered
    //val deaths: LiveData<String> = _deaths
}