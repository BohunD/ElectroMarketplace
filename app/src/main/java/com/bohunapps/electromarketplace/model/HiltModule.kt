package com.bohunapps.electromarketplace.model

import com.bohunapps.electromarketplace.model.repository.AuthRepo
import com.bohunapps.electromarketplace.model.repository.AuthRepoImpl
import com.bohunapps.electromarketplace.model.repository.FirestoreRepo
import com.bohunapps.electromarketplace.model.repository.FirestoreRepoImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class HiltModule {

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    fun provideFirebaseDB(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    fun provideAuthRepo(auth: FirebaseAuth, db: FirebaseFirestore): AuthRepo =
        AuthRepoImpl(auth, db)

    @Provides
    fun provideFirestoreRepo(): FirestoreRepo = FirestoreRepoImpl()
}