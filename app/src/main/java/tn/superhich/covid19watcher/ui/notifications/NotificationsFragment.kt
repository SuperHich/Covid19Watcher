package tn.superhich.covid19watcher.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tn.superhich.covid19watcher.R

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val spinner = root.findViewById<AppCompatSpinner>(R.id.countrySpinner)
        spinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                spinner.setSelection(position)
                Toast.makeText(
                    context,
                    "selected country: " + notificationsViewModel.localCountryList.value?.get(position),
                    Toast.LENGTH_LONG
                ).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
        val countryList = root.findViewById<RecyclerView>(R.id.countryList)
        countryList.layoutManager = LinearLayoutManager(requireContext())

        notificationsViewModel.localCountryList.observe(viewLifecycleOwner, Observer {
            spinner.adapter = CountrySpinnerAdapter(requireContext(), it)
            countryList.adapter = CountryListAdapter(requireContext(), it.filterNot { country -> country.code == "all" })
        })

        return root
    }

}