package com.bohunapps.electromarketplace

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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

sealed class Destination(val route: String){
    object Home: Destination("home")
    object Favorites: Destination("favorites")
    object NewAdvertisement: Destination("new_advertisement")
    object Comparison: Destination("comparison")
    object Profile: Destination("profile")
    object Advertisement: Destination("advertisements/{adId}"){
        fun createRoute(adId: Int) = "advertisements/$adId"
    }
    object SignIn: Destination("signIn")
    object SignUp: Destination("signUp")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ElectroMarketplaceTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    InitMainActivity()
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InitMainActivity(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.Home.route ){
        composable(Destination.Home.route){ HomeScreen()}
        composable(Destination.Favorites.route){ Favorites() }
        composable(Destination.NewAdvertisement.route){ NewAdScreen() }
        composable(Destination.Comparison.route){ ComparisonScreen() }
        composable(Destination.Profile.route){ ProfileScreen() }
        composable(Destination.SignIn.route){ SignInScreen() }
        composable(Destination.SignUp.route){ SignUpScreen() }
    }

    Scaffold(bottomBar = {
        BottomNavigationBar(navController = navController)
    }) {

    }
}

