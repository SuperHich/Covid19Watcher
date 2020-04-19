package tn.superhich.covid19watcher.ui.notifications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.data.CountryManager
import tn.superhich.covid19watcher.data.model.LocalCountry

class CountryListAdapter(private val context: Context, private val countries: List<LocalCountry>) :
    RecyclerView.Adapter<CountryListAdapter.CountryHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountryHolder {
        val lineView = LayoutInflater.from(parent.context)
            .inflate(R.layout.country_list_item, parent, false)
        return CountryHolder(lineView)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {
        holder.flag.setImageResource(
            CountryManager().getCountryDrawableId(
                context = context,
                countryCode = "country_" + countries[position].code.toLowerCase()
            ))
        holder.countryName.text = countries[position].name
        holder.totalCase.text = "TODO"
        holder.recovered.text = "TODO"
        holder.deaths.text = "TODO"
    }


    class CountryHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val flag = v.findViewById<ImageView>(R.id.country_flag)
        val countryName = v.findViewById<TextView>(R.id.countryName)
        val totalCase = v.findViewById<TextView>(R.id.totalCase)
        val recovered = v.findViewById<TextView>(R.id.recovered)
        val deaths = v.findViewById<TextView>(R.id.deaths)
        init {
            v.setOnClickListener(this)
        }
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

    }
}