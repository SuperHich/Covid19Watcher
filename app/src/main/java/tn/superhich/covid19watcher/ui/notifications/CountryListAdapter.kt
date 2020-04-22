package tn.superhich.covid19watcher.ui.notifications

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.data.CountryManager
import tn.superhich.covid19watcher.data.model.CountryTotalItem

class CountryListAdapter(
    private val context: Context,
    private val countries: List<CountryTotalItem>
) :
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
        return countries.size + 1
    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {

        if (position == 0) {
            holder.flag.setImageResource(R.drawable.country_all)
            holder.countryName.text = "Country"
            holder.totalCase.text = "Total Cases"
            holder.recovered.text = "Recovered"
            holder.deaths.text = "Deaths"
        } else {

            holder.flag.setImageResource(
                CountryManager().getCountryDrawableId(
                    context = context,
                    countryCode = countries[position - 1].code
                )
            )
            holder.countryName.text = countries[position - 1].title
            holder.totalCase.text = "${countries[position - 1].totalCases}"
            holder.recovered.text = "${countries[position - 1].totalRecovered}"
            holder.deaths.text = "${countries[position - 1].totalDeaths}"
        }
    }


    class CountryHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        val flag = v.findViewById<ImageView>(R.id.country_flag)
        val countryName = v.findViewById<TextView>(R.id.countryName)
        val totalCase = v.findViewById<TextView>(R.id.totalCase)
        val recovered = v.findViewById<TextView>(R.id.recovered)
        val deaths = v.findViewById<TextView>(R.id.deaths)
        val itemLayout = v.findViewById<LinearLayout>(R.id.item_layout)

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

    }
}