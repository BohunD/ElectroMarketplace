package com.bohunapps.electromarketplace.model

import android.util.Log
import androidx.navigation.NavController
import com.bohunapps.electromarketplace.Constants.USERS_TABLE
import com.bohunapps.electromarketplace.Destination
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface FirebaseRepo {
    val currentUser: FirebaseUser?

    suspend fun signIn(email:String, password: String): NetworkResult<FirebaseUser>
    suspend fun signUp(name: String, email:String, phone: String, password: String): NetworkResult<FirebaseUser>
    fun signOut()
}







