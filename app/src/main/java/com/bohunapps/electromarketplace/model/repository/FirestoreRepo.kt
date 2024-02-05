package com.bohunapps.electromarketplace.model.repository

import com.bohunapps.electromarketplace.model.NetworkResult
import com.bohunapps.electromarketplace.model.models.Advertisement
import com.bohunapps.electromarketplace.model.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.flow.StateFlow

interface FirestoreRepo {
    val firebaseDB: FirebaseFirestore
    val firebaseAuth: FirebaseAuth
    val userInfo: StateFlow<NetworkResult<User>>
     val firebaseStorage: StorageReference
    val userId: String?
    val adId: DocumentReference
    suspend fun getUserInfo(): NetworkResult<User?>

    suspend fun postAdvertisement(ad: Advertisement)

}