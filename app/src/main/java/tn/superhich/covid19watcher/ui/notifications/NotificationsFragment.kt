package tn.superhich.covid19watcher.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.global_info_layout.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_notifications, container, false)

        val listArray: ArrayList<KeyPairBoolData> = ArrayList()
        val spinner = root.findViewById<MultiSpinnerSearch>(R.id.countrySpinner)

        val countryList = root.findViewById<RecyclerView>(R.id.countryList)
        countryList.layoutManager = LinearLayoutManager(requireContext())

        // Loading methodes Section
        notificationsViewModel.loadCountryTotalslInfo()
        notificationsViewModel.loadGlobalInfo()

        //LiveData Observer Section
        notificationsViewModel.countryTotalsList.observe(viewLifecycleOwner, Observer {
            //Updating List
            countryList.adapter = CountryListAdapter(
                requireContext(),
                it.filterNot { country -> country.code == "all" })


            // For spinner
            for (i in it.indices) {
                val h = KeyPairBoolData()
                h.id = i + 1.toLong()
                h.name = it[i].title
                h.isSelected = false
                listArray.add(h)
            }

            spinner.setItems(
                listArray,
                -1
            ) { items ->

                val listToShow: ArrayList<String> = ArrayList()

                for (i in items.indices) {
                    if (items[i].isSelected) {
                        listToShow.add(items[i].name)
                        Log.i(
                            "TAG",
                            "$i : " + items[i]
                                .name + " : " + items[i].isSelected
                        )
                    }
                }
                notificationsViewModel.updateListToShow(listToShow)
            }
        })
        notificationsViewModel.totalInfo.observe(viewLifecycleOwner, Observer {
            affectedValue.text = it?.totalCases.toString()
            recoveredValue.text = it?.totalRecovered.toString()
            deathValue.text = it?.totalDeaths.toString()
        })


        return root
    }

}
