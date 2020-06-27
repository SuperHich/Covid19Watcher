package tn.superhich.covid19watcher.ui.global

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
import tn.superhich.covid19watcher.helper.StringHelper

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
            holder.countryName.text = context.getString(R.string.country)
            holder.totalCase.text = context.getString(R.string.total_cases)
            holder.recovered.text = context.getString(R.string.recovered)
            holder.deaths.text = context.getString(R.string.deaths)
        } else {

            holder.flag.setImageResource(
                CountryManager().getCountryDrawableId(
                    context = context,
                    countryCode = countries[position - 1].code
                )
            )
            val item = countries[position - 1]
            with(item) {
                holder.countryName.text = title
                holder.totalCase.text = StringHelper.formatNumber(totalCases)
                holder.deaths.text = StringHelper.formatNumber(totalDeaths)

                val calculatedTotalRecovered = totalCases - totalActiveCases - totalDeaths
                holder.recovered.text = if(totalRecovered  == 0 && calculatedTotalRecovered > 0) {
                    StringHelper.formatNumber(calculatedTotalRecovered)
                } else {
                    StringHelper.formatNumber(totalRecovered)
                }
            }
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