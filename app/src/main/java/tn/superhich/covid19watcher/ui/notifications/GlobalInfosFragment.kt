package tn.superhich.covid19watcher.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidbuts.multispinnerfilter.KeyPairBoolData
import com.androidbuts.multispinnerfilter.MultiSpinnerSearch
import kotlinx.android.synthetic.main.global_info_layout.*
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.helper.StringHelper
import tn.superhich.covid19watcher.ui.home.HomeViewModel

class GlobalInfosFragment : Fragment() {

    private lateinit var globalInfosViewModel: GlobalInfosViewModel
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        globalInfosViewModel = ViewModelProvider(this).get(GlobalInfosViewModel::class.java)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_global, container, false)
        root.setOnTouchListener{ _, _ -> true }

        val listArray: ArrayList<KeyPairBoolData> = ArrayList()
        val spinner = root.findViewById<MultiSpinnerSearch>(R.id.countrySpinner)

        val countryList = root.findViewById<RecyclerView>(R.id.countryList)
        countryList.layoutManager = LinearLayoutManager(requireContext())

        // Loading methodes Section
        globalInfosViewModel.loadCountryTotalslInfo()
        globalInfosViewModel.loadGlobalInfo()

        //LiveData Observer Section
        globalInfosViewModel.countryTotalsList.observe(viewLifecycleOwner, Observer {
            //Updating List
            countryList.adapter = CountryListAdapter(
                requireContext(),
                it.filterNot { country -> country.code == "all" }.sortedByDescending { item -> item.totalCases })


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
                globalInfosViewModel.updateListToShow(listToShow)
            }
        })
        globalInfosViewModel.totalInfo.observe(viewLifecycleOwner, Observer {
            affectedValue.text = StringHelper.formatNumber(it?.totalCases)
            recoveredValue.text = StringHelper.formatNumber(it?.totalRecovered)
            deathValue.text = StringHelper.formatNumber(it?.totalDeaths)
        })


        return root
    }

}
