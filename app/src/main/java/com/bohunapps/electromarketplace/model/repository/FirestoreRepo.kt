package com.bohunapps.electromarketplace.model.repository

import com.bohunapps.electromarketplace.model.NetworkResult
import com.bohunapps.electromarketplace.model.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.StateFlow

interface FirestoreRepo {
    val firebaseDB: FirebaseFirestore
    val firebaseAuth: FirebaseAuth
    val userInfo: StateFlow<NetworkResult<User>>

    suspend fun getUserInfo(): NetworkResult<User?>

}