package com.example.restaurantsexplorer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.restaurantsexplorer.Adapters.CityAdapter
import com.example.restaurantsexplorer.Adapters.CityItemClicked
import com.example.restaurantsexplorer.ViewModelFactory.MainViewModelFactory
import com.example.restaurantsexplorer.databinding.ActivityMainBinding
import com.example.restaurantsexplorer.models.City
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity(),CityItemClicked{

    private lateinit var viewModel: MainViewModel
    private lateinit var mAdapter: CityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MainViewModel::class.java)
        val binding : ActivityMainBinding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = CityAdapter(this)
        binding.recyclerView.adapter = mAdapter
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                fetchData(binding)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })

    }
    fun fetchData(binding: ActivityMainBinding) {
        val qs = binding.searchBar.text.toString()
        val params = HashMap<String, String>()
        params["user-key"] = "1916e1a18db00c06f8b0653ae41fe89d"
        params["Accept"] = "application/json"
        val url = "https://developers.zomato.com/api/v2.1/cities?q=$qs"
        val req: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            object : Response.Listener<JSONObject?> {
                override fun onResponse(response: JSONObject?) {
                    try {
                        val cityarray = ArrayList<City>()
                        val Error = response?.getString("status")
                        if (Error == "success") {
                            val jsonArray = response.getJSONArray("location_suggestions")
                            for (i in 0 until jsonArray.length()) {
                                val cityObject = jsonArray.getJSONObject(i)
                                val city = City(
                                    cityObject.getString("name"),
                                    cityObject.getString("country_flag_url"),
                                    cityObject.getInt("id")
                                )
                                cityarray.add(city)
                            }
                            mAdapter.updateCities(cityarray)
                        }
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            },
            object : Response.ErrorListener {
                override fun onErrorResponse(error: VolleyError?) {

                }
            }) {

            override fun getHeaders(): Map<String, String> {
                val headers = HashMap<String, String>()
                headers["user-key"] = "1916e1a18db00c06f8b0653ae41fe89d"
                headers["Accept"] = "application/json"
                return headers
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(req)
    }
    override fun onItemClicked(item: City) {
        val intent = Intent(this,CityRestaurants::class.java)
        val n = item.name
        val p = item.id
        intent.putExtra(CityRestaurants.city_name , n)
        intent.putExtra(CityRestaurants.city_id , p)
        startActivity(intent)
    }
}