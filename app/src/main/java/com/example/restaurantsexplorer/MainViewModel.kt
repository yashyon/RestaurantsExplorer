package com.example.restaurantsexplorer

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.restaurantsexplorer.Adapters.CityAdapter
import com.example.restaurantsexplorer.Adapters.CityItemClicked
import com.example.restaurantsexplorer.databinding.ActivityMainBinding
import com.example.restaurantsexplorer.models.City
import org.json.JSONException
import org.json.JSONObject

class MainViewModel(application: Application) : AndroidViewModel(application){

    private lateinit var binding: ActivityMainBinding
    private lateinit var mAdapter: CityAdapter
    private val context = getApplication<Application>().applicationContext

    override fun onCleared() {
        super.onCleared()
    }
}
