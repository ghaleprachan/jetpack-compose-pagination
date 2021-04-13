package com.example.paginationincompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(private val photoRepository: PhotoRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
/*        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
        } else {
            T
        }*/
        return MainViewModel(photoRepository) as T
    }
}