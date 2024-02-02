package com.bohunapps.electromarketplace.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bohunapps.electromarketplace.Destination
import com.bohunapps.electromarketplace.R
import com.bohunapps.electromarketplace.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    vm: AuthViewModel,
) {

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,

            modifier = Modifier
                .fillMaxHeight(0.45f)
                .fillMaxWidth()
        ) {
            Button(
                onClick = {
                    vm.signOut()
                    navController.navigate(Destination.SignIn.route) {
                        popUpTo(Destination.SignIn.route) { inclusive = false }
                        launchSingleTop = true
                    }
                }, colors = ButtonDefaults.buttonColors(containerColor = Color.DarkGray),
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                Text(text = "Sign out", modifier = Modifier.padding(start = 4.dp, end = 4.dp))
            }
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = null,
                modifier = Modifier
                    .size(140.dp)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .border(width = 2.dp, color = Color.Gray, shape = CircleShape)
                    .padding(top = 15.dp)
            )
            val scope = rememberCoroutineScope()
            scope.launch(Dispatchers.IO) {
                vm.getUserInfo()
            }
            val name = remember {
                mutableStateOf("")
            }
            val phone = remember {
                mutableStateOf("")
            }
            SideEffect {
                scope.launch {

                    vm.userFlow.collect {
                        name.value = it?.username ?: ""
                        phone.value = it?.phone ?: ""
                    }
                }
            }
            Log.e("NAME", name.value)
            Text(
                text = name.value,
                fontSize = 24.sp,
                modifier = Modifier.padding(top = 10.dp),
                fontWeight = FontWeight.Bold
            )
            Text(text = phone.value, fontSize = 22.sp, modifier = Modifier.padding(top = 10.dp))
        }
    }


}