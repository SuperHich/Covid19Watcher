package tn.superhich.covid19watcher.ui.notifications

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.data.CountryManager
import tn.superhich.covid19watcher.data.model.LocalCountry

class CountrySpinnerAdapter(
    private val context: Context,
    localCountryList: List<LocalCountry>
) :
    BaseAdapter() {

    private val mInflater: LayoutInflater = LayoutInflater.from(context)
    private var data = localCountryList
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.country_spinner_item, parent, false)
            vh = ItemRowHolder(view)
            view.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }
        vh.label.text = data[position].name
        vh.flag.setImageResource(
            CountryManager().getCountryDrawableId(
                context = context,
                countryCode = data[position].code
            )
        )
        return view
    }

    override fun getItem(position: Int): Any {
        return data[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return data.size
    }

    fun setData(data: List<LocalCountry>) {
        this.data = data
    }


    private class ItemRowHolder(row: View?) {

        val label: TextView = row?.findViewById(R.id.country_name) as TextView
        val flag: ImageView = row?.findViewById(R.id.country_flag) as ImageView

    }

}