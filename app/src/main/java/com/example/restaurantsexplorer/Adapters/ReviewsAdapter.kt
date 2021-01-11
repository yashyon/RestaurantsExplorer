package com.example.restaurantsexplorer.Adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextClock
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.example.restaurantsexplorer.R
import com.example.restaurantsexplorer.databinding.ReviewItemBinding
import com.example.restaurantsexplorer.models.Review


class ReviewsAdapter : RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder>() {

    private val reviews: ArrayList<Review> = ArrayList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ReviewsAdapter.ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_item, parent, false)
        val viewHolder = ReviewViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val currentItem = reviews[position]
        holder.name.text = currentItem.name
        holder.rating.text = currentItem.rating
        holder.reviewText.text = currentItem.text
        holder.reviewTime.text = currentItem.ratingTime
        val c = currentItem.ratingColor
        holder.ratingCard.setCardBackgroundColor(Color.parseColor("#$c"))
        holder.ratingText.text = currentItem.ratingText
        Glide.with(holder.itemView.context).load(currentItem.imageUrl).into(holder.image)
        val d = currentItem.foodieColor
        holder.imageCard.setCardBackgroundColor(Color.parseColor("#$d"))
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    class ReviewViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val name:TextView = itemView.findViewById(R.id.name)
        val rating : TextView = itemView.findViewById(R.id.rating)
        val ratingText : TextView= itemView.findViewById(R.id.rating_text)
        val ratingCard : CardView = itemView.findViewById(R.id.rating_card)
        val reviewText : TextView= itemView.findViewById(R.id.review_text)
        val image : ImageView= itemView.findViewById(R.id.image)
        val imageCard : CardView=itemView.findViewById(R.id.image_card)
        val reviewTime: TextView = itemView.findViewById(R.id.time)
    }

    fun updateReviews(updatedReviews: ArrayList<Review>) {
        reviews.clear()
        reviews.addAll(updatedReviews)
        notifyDataSetChanged()
    }

}