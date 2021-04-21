package com.example.newsapp.data.network

import com.example.newsapp.data.model.ArticlesResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface APIService {

    @GET("top-headlines")
    suspend fun getAll(
        @Query("apiKey") key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): ArticlesResponse


    @GET("top-headlines")
    suspend fun getCategory(
        @Query("apiKey") key: String,
        @Query("language") language: String,
        @Query("category") category: String,
        @Query("page") page: Int
    ): ArticlesResponse
}