package com.example.restaurantsexplorer.ViewModelFactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.restaurantsexplorer.MainViewModel
import com.example.restaurantsexplorer.RestaurantInfoViewModel

class RestaurantInfoViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantInfoViewModel::class.java)) {
            return RestaurantInfoViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}