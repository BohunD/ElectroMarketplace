package com.bohunapps.electromarketplace.viewmodel

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bohunapps.electromarketplace.Constants.DATA_AD_PHOTOS
import com.bohunapps.electromarketplace.model.models.Advertisement
import com.bohunapps.electromarketplace.model.models.Category
import com.bohunapps.electromarketplace.model.repository.FirestoreRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class NewAdViewModel @Inject constructor(private val repo: FirestoreRepo) : ViewModel() {
    val photos = mutableStateListOf<Uri?>(null)
    val selectedCategory = MutableStateFlow<Category?>(null)
    val filtersForCategory = MutableStateFlow<Map<String, ArrayList<String>>>(mapOf())
    val selectedFilter = MutableStateFlow<MutableMap<String, String>>(mutableMapOf())
    var photosList: MutableList<String> = mutableListOf()
    private var productName = ""
    private var productPrice = ""
    private var productCity = ""
    private var productDescription = ""

    fun clear() {
        viewModelScope.launch {
            delay(2000)
            photos.clear()
            selectedCategory.value = (null)
            filtersForCategory.value = mapOf()
            selectedFilter.value = mutableMapOf()
            photosList = mutableListOf()
            productName = ""
            productPrice = ""
            productCity = ""
            productDescription = ""
        }

    }

    suspend fun postAdvertisement() {
        val adId = repo.adId

        for (photo in photos) {
            if(photo!=null) {
                storeImage(photo)
                photosList.add(photo.toString())
            }
        }
            val ad = Advertisement(
                adId.id,
                photosList,
                productName,
                productPrice,
                selectedCategory.value?.name,
                selectedFilter.value,
                productDescription,
                productCity,
                System.currentTimeMillis(),
                repo.userId!!
            )
            Log.e("POST", ad.toString())
            repo.postAdvertisement(ad)
    }

    fun storeImage(imageUri: Uri?) {
        imageUri?.let {
            val uniqueImageName = "${System.currentTimeMillis()}_${UUID.randomUUID()}"
            val filePath =
                repo.firebaseStorage.child(DATA_AD_PHOTOS).child(repo.userId!!)
                    .child(uniqueImageName)
            filePath.putFile(imageUri)
                .addOnSuccessListener {
                    Log.e("StoreImage", "SUCCESS")
                }.addOnFailureListener {
                    it.printStackTrace()
                    Log.e("StoreImage", "Failure")
                }
        }
    }

    fun validateInput(): Boolean {
        var flag = true
        if (photos.isEmpty()) {
            Log.e("VALIDATE", "photos.isEmpty()")
            flag = false
        }
        if (selectedCategory.value == null) {
            Log.e("VALIDATE", "selectedCategory.value == null")
            flag = false
        }
        if (selectedFilter.value.isEmpty()) {
            Log.e("VALIDATE", "selectedFilter.value.isEmpty()")
            flag = false
        }
        if (productCity.isEmpty()) {
            Log.e("VALIDATE", "productCity.isEmpty()")
            flag = false
        }
        if (productPrice.isEmpty()) {
            Log.e("VALIDATE", "productPrice.isEmpty()")
            flag = false
        }
        if (productDescription.isEmpty()) {
            Log.e("VALIDATE", "productDescription.isEmpty()")
            flag = false
        }
        if (productName.isEmpty()) {
            Log.e("VALIDATE", "productName.isEmpty()")
            flag = false
        }
    Log.e("FLAG: ", flag.toString())
    return flag
}

fun setName(name: String) {
    productName = name
    Log.e("setName", name)
}

fun setPrice(price: String) {
    productPrice = price
    Log.e("setPrice", price)

}

fun setCity(city: String) {
    productCity = city
    Log.e("setCity", city)

}

fun setDescription(desc: String) {
    productDescription = desc
    Log.e("setDescription", desc)

}

fun setSelectedCategory(category: Category) {
    Log.e("ViewMODEL", "category selected: $category")
    selectedCategory.value = category
    filtersForCategory.value = category.filters
}

fun addSelectedFilter(key: String, value: String) {
    selectedFilter.value[key] = value
    Log.e("ViewMODEL", "added filter: ${selectedFilter.value}")
}


fun addPhoto(url: Uri) {
    photos.add(url)
    Log.e("OnAdd", photos.toString())
}

fun deletePhoto(url: Uri) {
    photos.remove(url)
    Log.e("OnDelete", "${photos}")
}
}