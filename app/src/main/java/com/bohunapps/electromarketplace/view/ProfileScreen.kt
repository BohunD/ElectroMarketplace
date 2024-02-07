package com.bohunapps.electromarketplace.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Observer
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.bohunapps.electromarketplace.Destination
import com.bohunapps.electromarketplace.R
import com.bohunapps.electromarketplace.Util.getDateTimeFromLong
import com.bohunapps.electromarketplace.model.models.Advertisement
import com.bohunapps.electromarketplace.viewmodel.AuthViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun ProfileScreen(
    navController: NavHostController,
    paddingValues: PaddingValues,
    authViewModel: AuthViewModel,
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
                    authViewModel.signOut()
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
                authViewModel.getUserInfo()
            }
            val name = remember {
                mutableStateOf("")
            }
            val phone = remember {
                mutableStateOf("")
            }
            SideEffect {
                scope.launch {

                    authViewModel.userFlow.collect {
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
        ShowYourAds(authViewModel)
    }
}

@Composable
fun ShowYourAds(vm: AuthViewModel) {
    vm.getUserAds()
    val adsList by vm.adsLD.observeAsState()
    Log.e("PHOTOS11", adsList?.get(0)?.photos.toString())
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        adsList?.let {
            items(it) { ad ->
                AdItem(advertisement = ad)
            }
        }

    }
}

@Composable
fun AdItem(advertisement: Advertisement) {
    Column(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(3.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF8F6F6))
        //.border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .height(110.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFEEEBEB))
                .fillMaxWidth(),
            contentAlignment = Alignment.Center

        ) {
            if (advertisement.photos?.isNotEmpty() == true) {
                Log.e("IMAGE", advertisement.photos.get(0))
                AsyncImage(
                    advertisement.photos.get(0),
                    contentDescription = null,
                    modifier = Modifier
                        .height(120.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .padding(2.dp)

                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.no_image),
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .fillMaxSize()
                        .clip(RoundedCornerShape(8.dp))
                        .padding(2.dp)
                )
            }
        }
        Text(
            text = advertisement.name ?: "",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 3.dp, end = 3.dp)
        )
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.padding(start = 3.dp, end = 3.dp)
        ) {
            Text(text = advertisement.price ?: "", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "$",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 5.dp)
            )
        }
        Text(
            text = advertisement.city ?: "",
            color = Color.Gray,
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 3.dp, end = 3.dp)
        )
        Text(
            text = getDateTimeFromLong(advertisement.dateTime),
            color = Color.Gray,
            fontSize = 13.sp, modifier = Modifier.padding(start = 3.dp, end = 3.dp)
        )

    }
}