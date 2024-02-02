package com.bohunapps.electromarketplace.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Column(verticalArrangement = Arrangement.Center) {
        Text(text = "Home", fontSize = 50.sp)
    }

}