package tn.superhich.covid19watcher.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.data.model.LocalCountry
import tn.superhich.covid19watcher.helper.SharePrefs
import tn.superhich.covid19watcher.helper.StringHelper
import tn.superhich.covid19watcher.ui.notifications.CountrySpinnerAdapter


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    private var isGlobalData = false
    private var isToday = false
    private var isLoading = false

    private var myCountry: LocalCountry? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val tvActive: TextView = root.findViewById(R.id.tv_active)
        val tvSerious: TextView = root.findViewById(R.id.tv_serious)
        val tvConfirmed: TextView = root.findViewById(R.id.tv_confirmed_cases)
        val tvRecovered: TextView = root.findViewById(R.id.tv_recovered)
        val tvDeaths: TextView = root.findViewById(R.id.tv_deaths)

        val layoutRecoverd: LinearLayout = root.findViewById(R.id.layout_recovered)
        val layoutActive: LinearLayout = root.findViewById(R.id.layout_active)
        val layoutSerious: LinearLayout = root.findViewById(R.id.layout_serious)

        homeViewModel.totalInfo.observe(viewLifecycleOwner, Observer { totalInfo ->
            isLoading = false
            totalInfo?.let {
                tvConfirmed.text = StringHelper.formatNumber(it.totalCases)
                tvRecovered.text = StringHelper.formatNumber(it.totalRecovered)
                tvDeaths.text = StringHelper.formatNumber(it.totalDeaths)
                tvActive.text = StringHelper.formatNumber(it.totalUnresolved)
                tvSerious.text = StringHelper.formatNumber(it.totalSeriousCases)

                layoutRecoverd.visibility = View.VISIBLE
                layoutActive.visibility = View.VISIBLE
                layoutSerious.visibility = View.VISIBLE
            }
        })

        homeViewModel.todayTotalInfo.observe(viewLifecycleOwner, Observer { totalInfo ->
            isLoading = false
            totalInfo?.let {
                tvConfirmed.text = StringHelper.formatNumber(it.totalNewCasesToday)
                tvDeaths.text = StringHelper.formatNumber(it.totalNewDeathsToday)
                layoutRecoverd.visibility = View.GONE
                layoutActive.visibility = View.GONE
                layoutSerious.visibility = View.GONE
            }
        })

        homeViewModel.countryDataItem.observe(viewLifecycleOwner, Observer { countryDataItem ->
            isLoading = false
            countryDataItem?.let {
                tvConfirmed.text = StringHelper.formatNumber(it.totalCases)
                tvRecovered.text = StringHelper.formatNumber(it.totalRecovered)
                tvDeaths.text = StringHelper.formatNumber(it.totalDeaths)
                tvActive.text = StringHelper.formatNumber(it.totalActiveCases)
                tvSerious.text = StringHelper.formatNumber(it.totalSeriousCases)

                layoutRecoverd.visibility = View.VISIBLE
                layoutActive.visibility = View.VISIBLE
                layoutSerious.visibility = View.VISIBLE
            }
        })

        homeViewModel.todayCountryDataItem.observe(viewLifecycleOwner, Observer { totalInfo ->
            isLoading = false
            totalInfo?.let {
                tvConfirmed.text = StringHelper.formatNumber(it.totalNewCasesToday)
                tvDeaths.text = StringHelper.formatNumber(it.totalNewDeathsToday)
                layoutRecoverd.visibility = View.GONE
                layoutActive.visibility = View.GONE
                layoutSerious.visibility = View.GONE
            }
        })

        homeViewModel.error.observe(viewLifecycleOwner, Observer {
            isLoading = false
            it?.let {
                Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
            }
        })

        myCountry = SharePrefs(activity).getMyCountry()

        setupCountrySpinner(root)
        setupSwitchListener(root)
        setupPeriodListener(root)

        return root
    }

    private fun setupCountrySpinner(root: View) {
        val spinner = root.findViewById<AppCompatSpinner>(R.id.spinner_country)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinner.setSelection(position)

                val selectedCountry = spinner.getItemAtPosition(position) as LocalCountry
                SharePrefs(activity).saveMyCountry(selectedCountry)
                myCountry = selectedCountry
                refreshData()

            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        homeViewModel.localCountryList.observe(viewLifecycleOwner, Observer {
            val listOfCountry = it.filterNot { country -> country.code == "all" }
            spinner.adapter = CountrySpinnerAdapter(requireContext(), listOfCountry)
            val selectedPosition = listOfCountry.indexOfFirst { localCountry ->
                localCountry.code == myCountry?.code
            }
            spinner.setSelection(selectedPosition)
        })

    }

    private fun setupSwitchListener(root: View) {
        val tvSwitchOn: AppCompatButton = root.findViewById(R.id.tv_switch_on)
        val tvSwitchOff: AppCompatButton = root.findViewById(R.id.tv_switch_off)
        val btnSwitchOn: AppCompatButton = root.findViewById(R.id.btn_switch_on)
        val btnSwitchOff: AppCompatButton = root.findViewById(R.id.btn_switch_off)

        tvSwitchOn.setOnClickListener {
            tvSwitchOn.visibility = View.INVISIBLE
            tvSwitchOff.visibility = View.VISIBLE
            btnSwitchOn.visibility = View.VISIBLE
            btnSwitchOff.visibility = View.INVISIBLE
            isGlobalData = false
            refreshData()
        }

        tvSwitchOff.setOnClickListener {
            tvSwitchOn.visibility = View.VISIBLE
            tvSwitchOff.visibility = View.INVISIBLE
            btnSwitchOn.visibility = View.INVISIBLE
            btnSwitchOff.visibility = View.VISIBLE
            isGlobalData = true
            refreshData()
        }

    }

    private fun setupPeriodListener(root: View) {
        val tvOverall: TextView = root.findViewById(R.id.tv_overall)
        val tvToday: TextView = root.findViewById(R.id.tv_today)

        tvOverall.setOnClickListener {
            if(isToday) {
                tvOverall.setTextColor(resources.getColor(R.color.white))
                tvToday.setTextColor(resources.getColor(R.color.transparent_light))
                isToday = false
                refreshData()
            }
        }

        tvToday.setOnClickListener {
            if(!isToday) {
                tvOverall.setTextColor(resources.getColor(R.color.transparent_light))
                tvToday.setTextColor(resources.getColor(R.color.white))
                isToday = true
                refreshData()
            }

        }
    }

    override fun onResume() {
        super.onResume()
        refreshData()
    }

    private fun refreshData() {
        if(!isLoading) {
            isLoading = true
            if(isGlobalData) {
                homeViewModel.loadGlobalInfo(myCountry?.code, isToday)
            } else {
                homeViewModel.loadCountryTotal(myCountry?.code, isToday)
            }
        }
    }
}