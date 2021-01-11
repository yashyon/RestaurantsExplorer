package com.example.restaurantsexplorer

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.browser.customtabs.CustomTabsIntent
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.bumptech.glide.Glide
import com.example.restaurantsexplorer.ViewModelFactory.RestaurantInfoViewModelFactory
import com.example.restaurantsexplorer.databinding.ActivityRestaurantInfoBinding
import org.json.JSONException
import org.json.JSONObject

class RestaurantInfoActivity : AppCompatActivity() {

    private lateinit var viewModel: RestaurantInfoViewModel
    private var seeMoreUrl : String = ""
    private var seeMenuUrl : String = ""

    companion object {
        const val res_id: String = "resId"
        const val res_name: String = "resName"
        const val res_rating: String = "resRating"
        const val res_ratingColor: String = "resRatingColor"
        const val res_image: String = "resImage"
        const val res_address: String = "resAddress"
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant_info)

        val binding: ActivityRestaurantInfoBinding = setContentView(this, R.layout.activity_restaurant_info)

        val viewModelFactory = RestaurantInfoViewModelFactory(application)
        viewModel =
            ViewModelProvider(this, viewModelFactory).get(RestaurantInfoViewModel::class.java)
        val resName = intent.getStringExtra(res_name)
        val resRating = intent.getStringExtra(res_rating)
        val resRatingColor = intent.getStringExtra(res_ratingColor)
        val resId = intent.getStringExtra(res_id).toString()
        val resImage = intent.getStringExtra(res_image)
        val resAddress = intent.getStringExtra(res_address)

        binding.restaurantName.text = resName
        binding.restaurantRating.text = resRating
        Glide.with(this).load(resImage).into(binding.restaurantImage)
        val c = resRatingColor
        binding.restaurantRatingCard.setBackgroundColor(Color.parseColor("#$c"))
        binding.restaurantAddress.text = resAddress
        updateInfo(binding, resId.toString())

        binding.menuButton.setOnClickListener {
            showMenu()
        }
        binding.seeMoreButton.setOnClickListener {
            seeMore()
        }
        binding.seeReviewsButton.setOnClickListener {
            showReviews(resId,resName)
        }
    }

    private fun showReviews(resId: String, resName: String?) {
        val intent = Intent(this,ReviewsActivity::class.java)
        intent.putExtra(ReviewsActivity.restaurant_Id, resId)
        intent.putExtra(ReviewsActivity.restaurant_name,resName)
        startActivity(intent)
    }

    private fun seeMore() {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(seeMoreUrl))
    }

    private fun showMenu() {
        val builder =  CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(seeMenuUrl))
    }

    private fun updateInfo(binding: ActivityRestaurantInfoBinding, resId: String) {
        val params = HashMap<String, String>()
        params["user-key"] = "1916e1a18db00c06f8b0653ae41fe89d"
        params["Accept"] = "application/json"
        val url = "https://developers.zomato.com/api/v2.1/restaurant?res_id=$resId"
        val req: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            object : Response.Listener<JSONObject?> {
                override fun onResponse(response: JSONObject?) {
                    try {
                        val restaurantObject = response!!.getJSONObject("location")
                        binding.restaurantAddress.text = restaurantObject.getString("address")
                        binding.restaurantLocality.text = restaurantObject.getString("locality")
                        binding.restaurantCity.text = restaurantObject.getString("city")
                        binding.restaurantTiming.text = response.getString("timings")
                        binding.restaurantContact.text = response.getString("phone_numbers")
                        seeMoreUrl = response.getString("url")
                        seeMenuUrl = response.getString("menu_url")
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
}