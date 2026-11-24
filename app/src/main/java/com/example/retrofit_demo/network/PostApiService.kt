package com.example.retrofit_demo.network

import com.example.retrofit_demo.data.Post
import retrofit2.http.GET

interface PostApiService {
    @GET("posts")
    suspend fun getPosts(): List<Post>
}
