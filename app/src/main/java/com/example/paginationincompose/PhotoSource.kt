package com.example.paginationincompose

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.paginationincompose.models.Photo
import java.lang.Exception

class PhotoSource(private val photoRepository: PhotoRepository) : PagingSource<Int, Photo>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Photo> {
        return try {
            val page = params.key ?: 1
            val photoResponse = photoRepository.getPhotos(page)
            LoadResult.Page(
                data = photoResponse,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Photo>): Int? {
        return 1
    }
}