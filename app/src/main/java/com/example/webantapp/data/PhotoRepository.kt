package com.example.webantapp.data


import com.example.webantapp.model.Photo
import com.example.webantapp.network.ApiService
import javax.inject.Inject


interface PhotoRepository {
    suspend fun getPhotos(): List<Photo>
    suspend fun getPopularPhotos(): List<Photo>
    suspend fun getRandomPhotos(): List<Photo>
    suspend fun getPhotosOnQuery(query: String): List<Photo>
}

class DefaultPhotoRepository @Inject constructor(
    private val apiService: ApiService
) : PhotoRepository {
    override suspend fun getPhotos(): List<Photo> = apiService.getPhotos()
    override suspend fun getPopularPhotos(): List<Photo> = apiService.getPopularPhotos()
    override suspend fun getRandomPhotos(): List<Photo> = apiService.getRandomPhotos()
    override suspend fun getPhotosOnQuery(query: String): List<Photo> =
        apiService.getPhotosOnQuery(query = query).photos
}