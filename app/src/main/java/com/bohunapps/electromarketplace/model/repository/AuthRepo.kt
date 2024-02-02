package com.bohunapps.electromarketplace.model.repository

import com.bohunapps.electromarketplace.model.NetworkResult
import com.google.firebase.auth.FirebaseUser

interface AuthRepo {
    val currentUser: FirebaseUser?

    suspend fun signIn(email:String, password: String): NetworkResult<FirebaseUser>
    suspend fun signUp(name: String, email:String, phone: String, password: String): NetworkResult<FirebaseUser>
    fun signOut()
}







