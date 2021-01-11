package com.example.restaurantsexplorer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.restaurantsexplorer.Adapters.ReviewsAdapter
import com.example.restaurantsexplorer.databinding.ActivityReviewsBinding
import com.example.restaurantsexplorer.models.Restaurant
import com.example.restaurantsexplorer.models.Review
import org.json.JSONException
import org.json.JSONObject

class ReviewsActivity : AppCompatActivity() {

    private  lateinit var mAdapter:ReviewsAdapter

    companion object {
        const val restaurant_Id: String = "Restaurant_ID"
        const val restaurant_name: String = "Restaurant_Name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reviews)
        val restaurantId = intent.getStringExtra(restaurant_Id)
        val restaurantName = intent.getStringExtra(restaurant_name)
        val binding : ActivityReviewsBinding = DataBindingUtil.setContentView(this,R.layout.activity_reviews)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter = ReviewsAdapter()
        binding.recyclerView.adapter = mAdapter
        fetchData(restaurantId)
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

    private fun fetchData(restaurantId: String?) {
        val params = HashMap<String, String>()
        params["user-key"] = "1916e1a18db00c06f8b0653ae41fe89d"
        params["Accept"] = "application/json"
        val url = "https://developers.zomato.com/api/v2.1/reviews?res_id=$restaurantId"
        val req: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            object : Response.Listener<JSONObject?> {
                override fun onResponse(response: JSONObject?) {
                    try {
                        val reviewsArray = ArrayList<Review>()
                        val jsonArray = response!!.getJSONArray("user_reviews")
                        for (i in 0 until jsonArray.length()) {
                            val reviewObject = jsonArray.getJSONObject(i).getJSONObject("review")
                            val review = Review(
                                reviewObject.getJSONObject("user").getString("name"),
                                reviewObject.getString("rating"),
                                reviewObject.getJSONObject("user").getString("foodie_color"),
                                reviewObject.getString("rating_color"),
                                reviewObject.getString("rating_text"),
                                reviewObject.getString("review_time_friendly"),
                                reviewObject.getJSONObject("user").getString("profile_image"),
                                reviewObject.getString("review_text")
                            )
                            reviewsArray.add(review)
                        }
                        mAdapter.updateReviews(reviewsArray)
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