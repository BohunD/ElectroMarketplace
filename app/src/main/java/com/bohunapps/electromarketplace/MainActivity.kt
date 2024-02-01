package com.bohunapps.electromarketplace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.bohunapps.electromarketplace.ui.theme.ElectroMarketplaceTheme

sealed class Destination(val route: String){
    object Home: Destination("home")
    object Favorites: Destination("favorites")
    object NewAdvertisement: Destination("new_advertisement")
    object Comparison: Destination("comparison")
    object Profile: Destination("profile")
    object Advertisement: Destination("advertisements/{adId}"){
        fun createRoute(adId: Int) = "advertisements/$adId"
    }
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
                }
            }
        }
    }
}

@Composable
fun InitMainActivity(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Destination.Home.route ){
        
    }
}

