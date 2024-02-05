package com.bohunapps.electromarketplace.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bohunapps.electromarketplace.model.repository.AuthRepo
import com.bohunapps.electromarketplace.model.NetworkResult
import com.bohunapps.electromarketplace.model.models.User
import com.bohunapps.electromarketplace.model.repository.FirestoreRepo
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo: AuthRepo, private val firestoreRepo: FirestoreRepo) : ViewModel() {
    val incorrectName = MutableStateFlow(false)
    val incorrectEmail = MutableStateFlow(false)
    val incorrectPhone = MutableStateFlow(false)
    val incorrectPassword = MutableStateFlow(false)
    val incorrectEmailSignIn = MutableStateFlow(false)
    val incorrectPasswordSignIn = MutableStateFlow(false)

    private val _userFlow = MutableStateFlow<User?>(null)
    val userFlow: StateFlow<User?> = _userFlow


    private val _signInFlow = MutableStateFlow<NetworkResult<FirebaseUser>?>(null)
    val signInFlow: StateFlow<NetworkResult<FirebaseUser>?> = _signInFlow

    private val _signUpFlow = MutableStateFlow<NetworkResult<FirebaseUser>?>(null)
    val signUpFlow: StateFlow<NetworkResult<FirebaseUser>?> = _signUpFlow

    fun signUserUp(name: String, email: String, phone: String, password: String) {
        var proceed = true
        if (name.isEmpty() || name.length < 5|| name.length>20){
            incorrectName.value = true
            Log.e("AuthVM", "name error")
            proceed = false
        }
        if (email.isEmpty() || !email.contains('@') || !email.contains('.') || email.length < 10) {
            incorrectEmail.value = true
            Log.e("AuthVM", "email error")
            proceed = false
        }
        if (phone.isEmpty() || phone.length < 10) {
            incorrectPhone.value = true
            Log.e("AuthVM", "phone error")
            proceed = false
        }
        if (password.isEmpty() || password.length < 6) {
            Log.e("AuthVM", "password error")
            incorrectPassword.value = true
            proceed = false
        }
        if(proceed){
            viewModelScope.launch {
                _signUpFlow.value = NetworkResult.Loading()
                val result = authRepo.signUp(name, email,phone,password)
                Log.e("RESULT", result.toString())
                _signUpFlow.value = result
            }
        }
    }

    fun signUserIn( email: String, password: String) {
        var proceed = true
        if (email.isEmpty() || !email.contains('@') || !email.contains('.') || email.length < 10) {
            incorrectEmailSignIn.value = true
            Log.e("SIGNIN", "Email error")

            proceed = false
        }
        if (password.isEmpty() || password.length < 6) {
            incorrectPasswordSignIn.value = true
            Log.e("SIGNIN", "Password error")

            proceed = false
        }
        if(proceed){
            Log.e("SIGNIN", "Proceed")
            viewModelScope.launch {
                _signInFlow.value = NetworkResult.Loading()
                val result = authRepo.signIn( email,password)
                _signInFlow.value = result
            }
        }
    }

    fun signOut(){
        authRepo.signOut()
    }

    fun getUserInfo(){
        viewModelScope.launch {
            val result = firestoreRepo.getUserInfo()
            if (result.data != null) {
                _userFlow.value =result.data
                Log.e("GET_USER_INFO", "result.data != null")
            }

        }
    }
}