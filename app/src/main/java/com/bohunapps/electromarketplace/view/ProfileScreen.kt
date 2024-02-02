package com.bohunapps.electromarketplace.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bohunapps.electromarketplace.Destination
import com.bohunapps.electromarketplace.viewmodel.AuthViewModel

@Composable
fun ProfileScreen(navController: NavHostController, paddingValues: PaddingValues, vm: AuthViewModel) {
    Column(modifier = Modifier.padding(paddingValues).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally,) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier.fillMaxHeight(0.4f).fillMaxWidth()
        ) {
            Button(onClick = {
                vm.signOut()
                navController.navigate(Destination.SignIn.route){
                    popUpTo(Destination.SignIn.route){inclusive=false}
                    launchSingleTop = true
                }
            }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray)) {
                Text(text = "Sign out", modifier = Modifier.padding(start = 4.dp, end= 4.dp))
            }
        }
    }


}