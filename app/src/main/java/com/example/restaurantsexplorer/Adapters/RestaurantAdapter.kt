package com.example.restaurantsexplorer.Adapters

import android.graphics.Color
import android.text.style.BackgroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantsexplorer.R
import com.example.restaurantsexplorer.models.City
import com.example.restaurantsexplorer.models.Restaurant
import kotlinx.android.synthetic.main.restaurant_item.*

class RestaurantAdapter(private val listener : RestaurantItemClicked)
    :RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    val restaurants : ArrayList<Restaurant> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int): RestaurantAdapter.RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_item, parent, false)
        val viewHolder = RestaurantAdapter.RestaurantViewHolder(view)
        view.setOnClickListener {
            listener.onItemClicked(restaurants[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: RestaurantAdapter.RestaurantViewHolder, position: Int) {
        val currentItem = restaurants[position]
        holder.name.text = currentItem.name
        holder.address.text = currentItem.address
        holder.ratingText.text = currentItem.rating
        val c = currentItem.ratingColor
        holder.rating.setBackgroundColor(Color.parseColor("#$c"))
//        holder.ratingColorCard.setBackgroundColor()
        holder.ratingText.text = currentItem.ratingText
        holder.rating.text = currentItem.rating
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name : TextView = itemView.findViewById(R.id.restaurant_name)
        val address : TextView = itemView.findViewById(R.id.restaurant_address)
        val image : ImageView = itemView.findViewById(R.id.restaurant_image)
        val rating : TextView = itemView.findViewById(R.id.restaurant_rating)
        val ratingText : TextView = itemView.findViewById(R.id.restaurant_rating_text)
        val ratingColorCard : CardView = itemView.findViewById(R.id.restaurant_rating_card)
    }
    fun updateRestaurants(updatedRestaurants: ArrayList<Restaurant>) {
        restaurants.clear()
        restaurants.addAll(updatedRestaurants)
        notifyDataSetChanged()
    }
}
interface RestaurantItemClicked{
    fun onItemClicked(item: Restaurant)
}