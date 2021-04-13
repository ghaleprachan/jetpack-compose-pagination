package com.example.paginationincompose

import com.example.paginationincompose.models.Photo

interface PhotoRepository {
    suspend fun getPhotos(page: Int): List<Photo>
}

class IPhotoRepository(private val apiService: PhotoApiService) : PhotoRepository {
    override suspend fun getPhotos(page: Int): List<Photo> {
        return apiService.getPhotos(page)
    }
}