package com.bohunapps.electromarketplace.model.repository

import android.util.Log
import com.bohunapps.electromarketplace.Constants.USERS_TABLE
import com.bohunapps.electromarketplace.Util.awaitSuccess
import com.bohunapps.electromarketplace.model.NetworkResult
import com.bohunapps.electromarketplace.model.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow

class FirestoreRepoImpl : FirestoreRepo {
    override val firebaseDB: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()

    override val firebaseAuth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

    override val userInfo = MutableStateFlow<NetworkResult<User>>(NetworkResult.Initial())

    override suspend fun getUserInfo(): NetworkResult<User?> {
        return try {
            val result =
                firebaseDB.collection(USERS_TABLE).document(firebaseAuth.uid!!).get().awaitSuccess()
            Log.e("PROFILE_REPO", "TRY: $result")
            NetworkResult.Success(result.toObject(User::class.java))
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("PROFILE_REPO", "CATCH: ${e.message}")
            NetworkResult.Error(e.message.toString())

        }
    }

}