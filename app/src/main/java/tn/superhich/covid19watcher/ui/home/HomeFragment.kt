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
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.data.model.CountryDataItem
import tn.superhich.covid19watcher.data.model.LocalCountry
import tn.superhich.covid19watcher.data.model.TotalInfo
import tn.superhich.covid19watcher.helper.SharedPrefs
import tn.superhich.covid19watcher.helper.StringHelper
import tn.superhich.covid19watcher.ui.home.HomeViewModel.Companion.UNKNOWN_ERROR
import tn.superhich.covid19watcher.ui.notifications.CountrySpinnerAdapter


class HomeFragment : Fragment() {

    @BindView(R.id.tv_active)
    lateinit var tvActive: TextView

    @BindView(R.id.tv_serious)
    lateinit var tvSerious: TextView

    @BindView(R.id.tv_confirmed_cases)
    lateinit var tvConfirmed: TextView

    @BindView(R.id.tv_recovered)
    lateinit var tvRecovered: TextView

    @BindView(R.id.tv_deaths)
    lateinit var tvDeaths: TextView

    @BindView(R.id.layout_recovered)
    lateinit var layoutRecoverd: LinearLayout

    @BindView(R.id.layout_active)
    lateinit var layoutActive: LinearLayout

    @BindView(R.id.layout_serious)
    lateinit var layoutSerious: LinearLayout


    private lateinit var homeViewModel: HomeViewModel

    private var isGlobalData = false
    private var isToday = false
    private var isLoading = false

    private var myCountry: LocalCountry? = null

    private lateinit var unbinder: Unbinder


    override fun onDestroyView() {
        super.onDestroyView()
        unbinder.unbind()
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        unbinder = ButterKnife.bind(this, root)

        homeViewModel.totalInfo.observe(viewLifecycleOwner, Observer { totalInfo ->
            isLoading = false
            totalInfo?.let {
                updateInfo(totalInfo)
            }
        })

        homeViewModel.todayTotalInfo.observe(viewLifecycleOwner, Observer { totalInfo ->
            isLoading = false
            totalInfo?.let {
                updateInfo(totalInfo)
            }
        })

        homeViewModel.countryDataItem.observe(viewLifecycleOwner, Observer { countryDataItem ->
            isLoading = false
            countryDataItem?.let {
                updateInfo(countryDataItem)
            }
        })

        homeViewModel.todayCountryDataItem.observe(viewLifecycleOwner, Observer { countryDataItem ->
            isLoading = false
            countryDataItem?.let {
                updateInfo(countryDataItem)
            }
        })

        homeViewModel.error.observe(viewLifecycleOwner, Observer {
            isLoading = false
            it?.let {
                updateInfo(null)
                if(it == UNKNOWN_ERROR) {
                    Toast.makeText(activity, getString(R.string.unknown_error), Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        myCountry = SharedPrefs(activity).getMyCountry()

        setupCountrySpinner(root)
        setupSwitchListener(root)
        setupPeriodListener(root)

        return root
    }

    private fun updateInfo(totalInfo: TotalInfo?) {
        if(isToday) {
            tvConfirmed.text = StringHelper.formatNumber(totalInfo?.totalNewCasesToday)
            tvDeaths.text = StringHelper.formatNumber(totalInfo?.totalNewDeathsToday)

            layoutRecoverd.visibility = View.GONE
            layoutActive.visibility = View.GONE
            layoutSerious.visibility = View.GONE
        } else {
            tvConfirmed.text = StringHelper.formatNumber(totalInfo?.totalCases)
            tvRecovered.text = StringHelper.formatNumber(totalInfo?.totalRecovered)
            tvDeaths.text = StringHelper.formatNumber(totalInfo?.totalDeaths)
            tvActive.text = StringHelper.formatNumber(totalInfo?.totalUnresolved)
            tvSerious.text = StringHelper.formatNumber(totalInfo?.totalSeriousCases)

            layoutRecoverd.visibility = View.VISIBLE
            layoutActive.visibility = View.VISIBLE
            layoutSerious.visibility = View.VISIBLE
        }
    }

    private fun updateInfo(countryDataItem: CountryDataItem) {
        if(isToday) {
            tvConfirmed.text = StringHelper.formatNumber(countryDataItem.totalNewCasesToday)
            tvDeaths.text = StringHelper.formatNumber(countryDataItem.totalNewDeathsToday)

            layoutRecoverd.visibility = View.GONE
            layoutActive.visibility = View.GONE
            layoutSerious.visibility = View.GONE
        } else {
            tvConfirmed.text = StringHelper.formatNumber(countryDataItem.totalCases)
            tvRecovered.text = StringHelper.formatNumber(countryDataItem.totalRecovered)
            tvDeaths.text = StringHelper.formatNumber(countryDataItem.totalDeaths)
            tvActive.text = StringHelper.formatNumber(countryDataItem.totalActiveCases)
            tvSerious.text = StringHelper.formatNumber(countryDataItem.totalSeriousCases)

            layoutRecoverd.visibility = View.VISIBLE
            layoutActive.visibility = View.VISIBLE
            layoutSerious.visibility = View.VISIBLE
        }
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
                SharedPrefs(activity).saveMyCountry(selectedCountry)
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
                homeViewModel.loadGlobalInfo(isToday)
            } else {
                homeViewModel.loadCountryTotal(myCountry?.code, isToday)
            }
        }
    }
}