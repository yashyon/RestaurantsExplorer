package com.example.restaurantsexplorer

import android.app.Application
import android.graphics.Color
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.AndroidViewModel
import com.bumptech.glide.Glide
import com.example.restaurantsexplorer.Adapters.CityAdapter
import com.example.restaurantsexplorer.databinding.ActivityMainBinding
import com.example.restaurantsexplorer.databinding.ActivityRestaurantInfoBinding

class RestaurantInfoViewModel(application: Application) : AndroidViewModel(application){

//    private lateinit var binding : ActivityRestaurantInfoBinding
    private val context = getApplication<Application>().applicationContext


    override fun onCleared() {
        super.onCleared()
//
//        binding.restaurantName.text = RestaurantInfoActivity.resame
//        binding.restaurantRating.text = RestaurantInfoActivity.res_rating
//        Glide.with(context).load(RestaurantInfoActivity.res_image).into(binding.restaurantImage)
//        val c = RestaurantInfoActivity.res_ratingColor
//        binding.restaurantRatingCard.setBackgroundColor(Color.parseColor("#$c"))
    }

}

/*
* Received Things:-
* name
* rating
* ratingColor
* resId
* resImage
*
*
*
* */
