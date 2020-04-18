package tn.superhich.covid19watcher.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.data.RetrofitClient
import tn.superhich.covid19watcher.data.Services
import tn.superhich.covid19watcher.data.model.CountryData
import tn.superhich.covid19watcher.data.model.GlobalInfo


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        val tvActive: TextView = root.findViewById(R.id.tv_active)
        homeViewModel.active.observe(viewLifecycleOwner, Observer {
            tvActive.text = it
        })

        val tvSerious: TextView = root.findViewById(R.id.tv_serious)
        homeViewModel.serious.observe(viewLifecycleOwner, Observer {
            tvSerious.text = it
        })

        val tvConfirmed: TextView = root.findViewById(R.id.tv_confirmed_cases)
        homeViewModel.confirmed.observe(viewLifecycleOwner, Observer {
            tvConfirmed.text = it
        })

        val tvRecovered: TextView = root.findViewById(R.id.tv_recovered)
        homeViewModel.recovered.observe(viewLifecycleOwner, Observer {
            tvRecovered.text = it
        })

        val tvDeaths: TextView = root.findViewById(R.id.tv_deaths)
        homeViewModel.deaths.observe(viewLifecycleOwner, Observer {
            tvDeaths.text = it
        })


        return root
    }

    override fun onResume() {
        super.onResume()

        loadGlobalInfo()
        //loadCountryTimeline()

    }

    private fun loadGlobalInfo() {
        val service = RetrofitClient.getRetrofitInstance()?.create(Services::class.java)
        val call = service?.getGlobalInfo()
        call?.enqueue(object : Callback<GlobalInfo> {
            override fun onResponse(call: Call<GlobalInfo>, response: Response<GlobalInfo>) {
                //progressDoalog.dismiss()
                //generateDataList(response.body())
                Log.d("TAG", " " + response.body().toString())
                response.body()?.results?.first()?.let { totalInfo ->
                    homeViewModel.confirmed.value = "${totalInfo.totalCases}"
                    homeViewModel.recovered.value = "${totalInfo.totalRecovered}"
                    homeViewModel.deaths.value = "${totalInfo.totalDeaths}"
                    homeViewModel.active.value = "${totalInfo.totalUnresolved}"
                    homeViewModel.serious.value = "${totalInfo.totalSeriousCases}"
                }
            }

            override fun onFailure(call: Call<GlobalInfo>, t: Throwable) {
                //progressDoalog.dismiss()
                Toast.makeText(
                    activity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun loadCountryTimeline() {
        val service = RetrofitClient.getRetrofitInstance()?.create(Services::class.java)
        val call = service?.getCountryTotal("TN")
        call?.enqueue(object : Callback<CountryData> {
            override fun onResponse(call: Call<CountryData>, response: Response<CountryData>) {
                //progressDoalog.dismiss()
                //generateDataList(response.body())
                Log.d("TAG", " " + response.body().toString())
                response.body()?.countrytimelinedata?.first()?.let { totalInfo ->
                    homeViewModel.confirmed.value = "${totalInfo.totalCases}"
                    homeViewModel.recovered.value = "${totalInfo.totalRecovered}"
                    homeViewModel.deaths.value = "${totalInfo.totalDeaths}"
                    homeViewModel.active.value = "${totalInfo.totalUnresolved}"
                    homeViewModel.serious.value = "${totalInfo.totalSeriousCases}"
                }
            }

            override fun onFailure(call: Call<CountryData>, t: Throwable) {
                //progressDoalog.dismiss()
                Toast.makeText(
                    activity,
                    "Something went wrong...Please try later!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}