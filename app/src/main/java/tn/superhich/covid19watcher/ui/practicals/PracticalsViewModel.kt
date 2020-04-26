package tn.superhich.covid19watcher.ui.practicals

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.data.model.PracticalItem

class PracticalsViewModel : ViewModel() {

    private val _listSymptoms = MutableLiveData<List<PracticalItem>>().apply {
        value = listOf(
            PracticalItem(R.drawable.ic_symptom_1, R.string.symptom_1),
            PracticalItem(R.drawable.ic_symptom_2, R.string.symptom_2),
            PracticalItem(R.drawable.ic_symptom_3, R.string.symptom_3),
            PracticalItem(R.drawable.ic_symptom_4, R.string.symptom_4),
            PracticalItem(R.drawable.ic_symptom_5, R.string.symptom_5),
            PracticalItem(R.drawable.ic_symptom_6, R.string.symptom_6))
    }
    val listSymptoms: LiveData<List<PracticalItem>> = _listSymptoms

    private val _listAdvices = MutableLiveData<List<PracticalItem>>().apply {
        value = listOf(
            PracticalItem(R.drawable.ic_advice_1, R.string.advice_1),
            PracticalItem(R.drawable.ic_advice_2, R.string.advice_2),
            PracticalItem(R.drawable.ic_advice_3, R.string.advice_3),
            PracticalItem(R.drawable.ic_advice_4, R.string.advice_4),
            PracticalItem(R.drawable.ic_advice_5, R.string.advice_5),
            PracticalItem(R.drawable.ic_advice_6, R.string.advice_6))
    }
    val listAdvices: LiveData<List<PracticalItem>> = _listAdvices
}