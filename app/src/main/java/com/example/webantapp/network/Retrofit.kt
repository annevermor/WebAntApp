package com.example.webantapp.network

import com.example.webantapp.data.DefaultPhotoRepository
import com.example.webantapp.data.PhotoRepository
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

private const val baseUrl = "https://api.unsplash.com/"

private val json = Json { ignoreUnknownKeys = true }

private val retrofit: Retrofit = Retrofit.Builder()
    .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(baseUrl)
    .build()

private val retrofitService: ApiService by lazy {
    retrofit.create(ApiService::class.java)
}

val photoRepository: PhotoRepository by lazy {
    DefaultPhotoRepository(retrofitService)
}