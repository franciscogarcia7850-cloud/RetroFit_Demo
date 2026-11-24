package com.example.retrofit_demo.data

import com.google.gson.annotations.SerializedName

// Campos que devuelve la API por cada post
data class Post(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String,
    val link: String,
    @SerializedName("comment_count") val commentCount: Int
)
