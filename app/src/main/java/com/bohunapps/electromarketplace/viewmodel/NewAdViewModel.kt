package com.bohunapps.electromarketplace.viewmodel

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.graphics.ImageBitmap
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bohunapps.electromarketplace.Constants
import com.bohunapps.electromarketplace.model.models.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewAdViewModel: ViewModel() {
     val photos = mutableStateListOf<Uri?>(null)
    val selectedCategory = MutableStateFlow<Category?>(null)
    val filtersForCategory = MutableStateFlow<Map<String, ArrayList<String>>>(mapOf())
    val selectedFilter = MutableStateFlow<MutableMap<String,String>>(mutableMapOf())

    fun setSelectedCategory(category: Category){
        Log.e("ViewMODEL", "category selected: $category")
        selectedCategory.value = category
        filtersForCategory.value = category.filters
    }

    fun addSelectedFilter(key: String, value: String){
        selectedFilter.value[key] = value
        Log.e("ViewMODEL", "added filter: ${selectedFilter.value}")
    }


    fun addPhoto(url: Uri){
        photos.add(url)
        Log.e("OnAdd", "${photos}")
    }

    fun deletePhoto(url: Uri){
        photos.remove(url)
        Log.e("OnDelete", "${photos}")
    }
}