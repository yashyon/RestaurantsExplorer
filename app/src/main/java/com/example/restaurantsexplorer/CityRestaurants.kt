package com.example.restaurantsexplorer

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.restaurantsexplorer.Adapters.RestaurantAdapter
import com.example.restaurantsexplorer.Adapters.RestaurantItemClicked
import com.example.restaurantsexplorer.CityRestaurants.Companion.city_name
import com.example.restaurantsexplorer.databinding.ActivityCityRestaurantsBinding
import com.example.restaurantsexplorer.databinding.ActivityMainBinding
import com.example.restaurantsexplorer.models.City
import com.example.restaurantsexplorer.models.Restaurant
import kotlinx.android.synthetic.main.activity_city_restaurants.*
import org.json.JSONException
import org.json.JSONObject

class CityRestaurants : AppCompatActivity(), RestaurantItemClicked {

    private lateinit var mAdapter: RestaurantAdapter

    companion object {
        const val city_id: String = ""
        const val city_name: String = "City_Name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_city_restaurants)
        val entity = intent.getIntExtra(city_id, 0)
        val cityName = intent.getStringExtra(city_name)
        val binding: ActivityCityRestaurantsBinding = DataBindingUtil.setContentView(this, R.layout.activity_city_restaurants)
        val text = "List of Top-Rated Restaurants in $cityName City"
        binding.includedCityName.text = text
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = RestaurantAdapter(this)
        binding.recyclerView.adapter = mAdapter
        fetchData(entity)
    }
    override fun onOptionsItemSelected(item : MenuItem) : Boolean{
        when(item.itemId){
            android.R.id.home ->{
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out)
    }

    private fun fetchData(entity: Int) {
        val params = HashMap<String, String>()
        params["user-key"] = "1916e1a18db00c06f8b0653ae41fe89d"
        params["Accept"] = "application/json"
        val url =
            "https://developers.zomato.com/api/v2.1/location_details?entity_id=$entity&entity_type=city"
        val req: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            object : Response.Listener<JSONObject?> {
                override fun onResponse(response: JSONObject?) {
                    try {
                        val restaurantsArray = ArrayList<Restaurant>()
                        val jsonArray = response!!.getJSONArray("best_rated_restaurant")
                        val t = jsonArray.length().toString()
                        for (i in 0 until jsonArray.length()) {
                            val restaurantObject = jsonArray.getJSONObject(i).getJSONObject("restaurant")
                            val restaurant = Restaurant(
                                restaurantObject.getString("id"),
                                restaurantObject.getString("name"),
                                restaurantObject.getJSONObject("location").getString("address"),
                                restaurantObject.getJSONObject("user_rating").getString("aggregate_rating"),
                                restaurantObject.getJSONObject("user_rating").getString("rating_color"),
                                restaurantObject.getJSONObject("user_rating").getString("rating_text"),
                                restaurantObject.getString("featured_image")
                            )
                            restaurantsArray.add(restaurant)
                        }
                        mAdapter.updateRestaurants(restaurantsArray)
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

    override fun onItemClicked(item: Restaurant) {
        val intent = Intent(this,RestaurantInfoActivity::class.java)
        val id = item.resId
        val imageUrl = item.imageUrl
        val name = item.name
        val rating =  item.rating
        val ratingColor = item.ratingColor
        val resAddress = item.address
        intent.putExtra(RestaurantInfoActivity.res_id, id)
        intent.putExtra(RestaurantInfoActivity.res_name, name)
        intent.putExtra(RestaurantInfoActivity.res_image, imageUrl)
        intent.putExtra(RestaurantInfoActivity.res_rating, rating)
        intent.putExtra(RestaurantInfoActivity.res_ratingColor, ratingColor)
        intent.putExtra(RestaurantInfoActivity.res_address,resAddress)
        startActivity(intent)
    }
}