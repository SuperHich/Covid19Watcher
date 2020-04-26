package tn.superhich.covid19watcher.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import tn.superhich.covid19watcher.R

class PracticalsFragment : Fragment() {

    private lateinit var dashboardViewModel: PracticalsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dashboardViewModel =
            ViewModelProviders.of(this).get(PracticalsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_advices, container, false)
        root.setOnTouchListener{ _, _ -> true }

        val gridSymptoms: GridView = root.findViewById(R.id.gridview_symptoms)
        dashboardViewModel.listSymptoms.observe(viewLifecycleOwner, Observer {
            gridSymptoms.adapter = PracticalsAdapter(it)
        })

        val gridAdvices: GridView = root.findViewById(R.id.gridview_advices)
        dashboardViewModel.listAdvices.observe(viewLifecycleOwner, Observer {
            gridAdvices.adapter = PracticalsAdapter(it)
        })

        return root
    }
}