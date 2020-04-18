package tn.superhich.covid19watcher.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    val confirmed = MutableLiveData<String>().apply {
        value = this.value
    }

    val recovered = MutableLiveData<String>().apply {
        value = this.value
    }

    val deaths = MutableLiveData<String>().apply {
        value = this.value
    }

    val active = MutableLiveData<String>().apply {
        value = this.value
    }

    val serious = MutableLiveData<String>().apply {
        value = this.value
    }
}