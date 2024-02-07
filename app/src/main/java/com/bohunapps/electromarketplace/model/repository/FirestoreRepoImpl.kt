package com.bohunapps.electromarketplace.model.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bohunapps.electromarketplace.Constants
import com.bohunapps.electromarketplace.Constants.DATA_ADS
import com.bohunapps.electromarketplace.Constants.DATA_AD_USER_ID
import com.bohunapps.electromarketplace.Constants.USERS_TABLE
import com.bohunapps.electromarketplace.Util.awaitSuccess
import com.bohunapps.electromarketplace.model.NetworkResult
import com.bohunapps.electromarketplace.model.models.Advertisement
import com.bohunapps.electromarketplace.model.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class FirestoreRepoImpl : FirestoreRepo {
    override val firebaseDB: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()
    override val firebaseStorage: StorageReference
        get() =  FirebaseStorage.getInstance().reference
    override val userId = FirebaseAuth.getInstance().currentUser?.uid
    override val adId: DocumentReference
        get() = firebaseDB.collection(Constants.DATA_ADS).document()
    override val firebaseAuth: FirebaseAuth
        get() = FirebaseAuth.getInstance()

/*    override val adsList: MutableLiveData<List<Advertisement>>
        get() = MutableLiveData()*/

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

    override suspend fun postAdvertisement(ad: Advertisement) {
        adId.set(ad).addOnCompleteListener {
            Log.e("Repo Post","ADDED SUCCESSFULLY")
        }.addOnFailureListener {
            Log.e("Repo Post","ERROR: ${it.message}")
        }
    }

    override suspend fun getUsersAdvertisements(uid: String?): List<Advertisement> {
        val adverts = mutableListOf<Advertisement>()
        firebaseDB.collection(DATA_ADS).whereEqualTo(DATA_AD_USER_ID, uid).get()
            .addOnSuccessListener{ list->
                for (document in list.documents){
                    val ad = document.toObject(Advertisement::class.java)
                    ad.let {
                        adverts.add(it!!)
                    }
                }
                //adsList.value = adverts

            }.addOnFailureListener {
                Log.e("Repo User ads", "FAILURE: ${it.message}")
            }
        delay(1500)
        return adverts.sortedBy { it.dateTime }.reversed()
    }

    



}