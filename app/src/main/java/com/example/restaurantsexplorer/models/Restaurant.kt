package com.example.restaurantsexplorer.models

data class Restaurant(
    val resId : String,
    val name : String,
    val address : String,
    val rating : String,
    val ratingColor : String,
    val ratingText : String,
    val imageUrl : String
)

/*
*
* resId , name, address, aggregate_rating ,ratingText , image(thumb) , ratingColor
*
*
* */