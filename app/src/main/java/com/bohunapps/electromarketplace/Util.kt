package com.bohunapps.electromarketplace

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

object Util {
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun OutlinedBox(
        state: MutableState<String>,
        marTop: Int,
        label: String,
        keyboardType: KeyboardType,
    ) {
        OutlinedTextField(
            value = state.value,
            onValueChange = { state.value = it },
            modifier = Modifier
                .width(300.dp)
                .padding(top = marTop.dp),
            label = { Text(text = label) },
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                cursorColor = Color.DarkGray,
                focusedBorderColor = Color.DarkGray,
                focusedLabelColor = Color.DarkGray
            )
        )
    }
}