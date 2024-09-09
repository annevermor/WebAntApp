package com.example.webantapp.network


import com.example.webantapp.BuildConfig
import com.example.webantapp.model.Photo
import com.example.webantapp.model.SearchedPhoto
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = BuildConfig.API_KEY
private const val QUERY = "photos/?client_id=${API_KEY}&per_page=30"

interface ApiService {
    @GET(QUERY)
    suspend fun getPhotos(): List<Photo>

    @GET("${QUERY}&order_by=popular")
    suspend fun getPopularPhotos(): List<Photo>

    @GET("${QUERY}/random")
    suspend fun getRandomPhotos(): List<Photo>

    @GET("search/${QUERY}")
    suspend fun getPhotosOnQuery(@Query("query") query: String): SearchedPhoto

}