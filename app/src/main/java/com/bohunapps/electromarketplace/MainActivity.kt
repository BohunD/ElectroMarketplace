package com.bohunapps.electromarketplace

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bohunapps.electromarketplace.ui.theme.ElectroMarketplaceTheme
import com.bohunapps.electromarketplace.view.BottomNavigationBar
import com.bohunapps.electromarketplace.view.ComparisonScreen
import com.bohunapps.electromarketplace.view.Favorites
import com.bohunapps.electromarketplace.view.HomeScreen
import com.bohunapps.electromarketplace.view.NewAdScreen
import com.bohunapps.electromarketplace.view.ProfileScreen
import com.bohunapps.electromarketplace.view.SignInScreen
import com.bohunapps.electromarketplace.view.SignUpScreen
import com.bohunapps.electromarketplace.viewmodel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint

sealed class Destination(val route: String) {
    object Home : Destination("home")
    object Favorites : Destination("favorites")
    object NewAdvertisement : Destination("new_advertisement")
    object Comparison : Destination("comparison")
    object Profile : Destination("profile")
    object Advertisement : Destination("advertisements/{adId}") {
        fun createRoute(adId: Int) = "advertisements/$adId"
    }

    object SignIn : Destination("signIn")
    object SignUp : Destination("signUp")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val signUpVM by viewModels<AuthViewModel>()
    private val firebaseAuth = FirebaseAuth.getInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElectroMarketplaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InitMainActivity(signUpVM, firebaseAuth)
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitMainActivity(signUpVm: AuthViewModel, firebaseAuth: FirebaseAuth) {
    val navController = rememberNavController()
    val currentUser = firebaseAuth.currentUser
    val startDestination = if(currentUser== null){
        Destination.SignIn.route
    }else{
        Destination.Profile.route
    }
    Scaffold(
        content = {
            NavHost(navController = navController, startDestination = startDestination)  {
                composable(Destination.Home.route) { HomeScreen() }
                composable(Destination.Favorites.route) { Favorites() }
                composable(Destination.NewAdvertisement.route) { NewAdScreen() }
                composable(Destination.Comparison.route) { ComparisonScreen() }
                composable(Destination.Profile.route) { ProfileScreen() }
                composable(Destination.SignIn.route) { SignInScreen(navController) }
                composable(Destination.SignUp.route) { SignUpScreen(navController, signUpVm) }
            }
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        })
}

