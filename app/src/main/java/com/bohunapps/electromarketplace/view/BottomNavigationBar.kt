package com.bohunapps.electromarketplace.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bohunapps.electromarketplace.Destination
import com.bohunapps.electromarketplace.R


@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry.value?.destination
    val iconHome = painterResource(id = R.drawable.home)
    val iconFavorite = painterResource(id = R.drawable.like_filled)
    val iconAdd = painterResource(id = R.drawable.add)
    val iconCompare = painterResource(id = R.drawable.compare_filled)
    val iconProfile = painterResource(id = R.drawable.profile)


    if (currentDestination?.route != Destination.SignIn.route
        && currentDestination?.route != Destination.SignUp.route
        && currentDestination?.route != null
    ) {
        NavigationBar(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 10.dp)),
            containerColor = Color(0xFFDAD8D8),
            contentColor = Color(0xFFDAD8D8)
        )
        {
            NavigationBarItem(
                selected = currentDestination?.route == Destination.Home.route,
                onClick = {
                    navController.navigate(Destination.Home.route) {
                        popUpTo(Destination.Home.route)
                    }
                },
                icon = {
                    Icon(
                        iconHome,
                        contentDescription = "Home icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFDAD8D8))
            )


            NavigationBarItem(
                selected = currentDestination?.route == Destination.Favorites.route,
                onClick = {
                    navController.navigate(Destination.Favorites.route) {
                        popUpTo(Destination.Favorites.route)
                    }
                },
                icon = {
                    Icon(
                        iconFavorite,
                        contentDescription = "Favorites icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFDAD8D8))
            )


            NavigationBarItem(
                selected = currentDestination?.route == Destination.NewAdvertisement.route,
                onClick = {
                    navController.navigate(Destination.NewAdvertisement.route) {
                        popUpTo(Destination.NewAdvertisement.route)
                    }
                },
                icon = {
                    Icon(
                        iconAdd,
                        contentDescription = "New ad icon",
                        modifier = Modifier.size(40.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFDAD8D8))
            )


            NavigationBarItem(
                selected = currentDestination?.route == Destination.Comparison.route,
                onClick = {
                    navController.navigate(Destination.Comparison.route) {
                        popUpTo(Destination.Comparison.route)
                    }
                },
                icon = {
                    Icon(
                        iconCompare,
                        contentDescription = "Compare icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFDAD8D8))
            )


            NavigationBarItem(
                selected = currentDestination?.route == Destination.Profile.route,
                onClick = {
                    navController.navigate(Destination.Profile.route) {
                        popUpTo(Destination.Profile.route)
                    }
                },
                icon = {
                    Icon(
                        iconProfile,
                        contentDescription = "Profile icon",
                        modifier = Modifier.size(24.dp)
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color(0xFFDAD8D8))
            )
        }
    }

}