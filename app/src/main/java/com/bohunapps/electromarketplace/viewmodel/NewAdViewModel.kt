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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NewAdViewModel: ViewModel() {
     val photos = mutableStateListOf<Uri?>(null)


    fun addPhoto(url: Uri){
        photos.add(url)
        Log.e("OnAdd", "${photos}")
    }

    fun deletePhoto(url: Uri){
        photos.remove(url)
        Log.e("OnDelete", "${photos}")
    }
}