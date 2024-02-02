package com.bohunapps.electromarketplace.model.repository

import com.bohunapps.electromarketplace.Constants.USERS_TABLE
import com.bohunapps.electromarketplace.Util.await
import com.bohunapps.electromarketplace.model.NetworkResult
import com.bohunapps.electromarketplace.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import javax.inject.Inject

class AuthRepoImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: FirebaseFirestore
) : AuthRepo {

    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun signIn(email: String, password: String): NetworkResult<FirebaseUser> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            NetworkResult.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResult.Error(e.message!!)
        }

    }


    override suspend fun signUp(
        name: String,
        email: String,
        phone: String,
        password: String,
    ): NetworkResult<FirebaseUser> {
        return try{
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val user = User(name, email, phone, password)
            firebaseDB.collection(USERS_TABLE).document().set(user)
            NetworkResult.Success(result.user!!)
        }catch (e: Exception){
            e.printStackTrace()
            NetworkResult.Error(e.message!!)
        }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }
}