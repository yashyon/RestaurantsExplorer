package com.example.restaurantsexplorer.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantsexplorer.R
import com.example.restaurantsexplorer.models.City
import org.w3c.dom.Text


class CityAdapter(private val listener:CityItemClicked): RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val cities: ArrayList<City> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.city_item, parent, false)
        val viewHolder = CityAdapter.CityViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(cities[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CityAdapter.CityViewHolder, position: Int) {
        val currentItem = cities[position]
        holder.name.text = currentItem.name
        Glide.with(holder.itemView.context).load(currentItem.flagUrl).into(holder.flag)
    }

    override fun getItemCount(): Int {
        return cities.size
    }
    fun updateCities(updatedCities: ArrayList<City>) {
        cities.clear()
        cities.addAll(updatedCities)
        notifyDataSetChanged()
    }
    class CityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name : TextView = itemView.findViewById(R.id.name)
        val flag : ImageView = itemView.findViewById(R.id.flag)
    }
}
interface CityItemClicked{
    fun onItemClicked(item:City)
}