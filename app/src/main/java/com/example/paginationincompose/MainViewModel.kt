package com.example.paginationincompose

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.paginationincompose.models.Photo
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val photoRepository: PhotoRepository) : ViewModel() {
    fun getPhotoPagination(): Flow<PagingData<Photo>> {
        return Pager(PagingConfig(pageSize = 5)) {
            PhotoSource(photoRepository)
        }.flow
    }
}