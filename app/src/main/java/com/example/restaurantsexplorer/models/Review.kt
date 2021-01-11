package com.example.restaurantsexplorer.models

data class Review(
    val name: String,
    val rating : String,
    val foodieColor : String,
    val ratingColor: String,
    val ratingText: String,
    val ratingTime: String,
    val imageUrl: String,
    val text: String
)