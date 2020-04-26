package tn.superhich.covid19watcher.ui.practicals

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import tn.superhich.covid19watcher.R
import tn.superhich.covid19watcher.data.model.PracticalItem

class PracticalsAdapter(val practicals: List<PracticalItem>)
    : BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(parent?.context)
                .inflate(R.layout.item_gridview, parent, false)

            val imageView : ImageView = view.findViewById(R.id.iv_grid_item)
            val titleView : TextView = view.findViewById(R.id.tv_grid_item)

            view.setTag(ViewHolder(imageView, titleView))
        }

        val viewHolder = view?.tag as ViewHolder

        val item = practicals[position]

        viewHolder.icon.setImageResource(item.iconResId)
        viewHolder.title.setText(item.titleResId)

        return view
    }

    override fun getItem(position: Int): Any {
        return practicals[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return practicals.size
    }

    class ViewHolder(
        val icon: ImageView,
        val title: TextView
    )

}
