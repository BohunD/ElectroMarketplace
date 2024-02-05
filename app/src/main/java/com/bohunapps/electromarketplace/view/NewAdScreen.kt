package com.bohunapps.electromarketplace.view

import android.annotation.SuppressLint

import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bohunapps.electromarketplace.Constants
import com.bohunapps.electromarketplace.CustomDropdownMenu
import com.bohunapps.electromarketplace.CustomDropdownMenuStr
import com.bohunapps.electromarketplace.Destination
import com.bohunapps.electromarketplace.R
import com.bohunapps.electromarketplace.viewmodel.NewAdViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("CoroutineCreationDuringComposition", "StateFlowValueCalledInComposition")
@Composable
fun NewAdScreen(
    navController: NavHostController,
    vm: NewAdViewModel,
    paddingValues: PaddingValues,
) {
    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val nameText = remember { mutableStateOf("") }
    val priceText = remember { mutableStateOf("") }
    val cityText = remember { mutableStateOf("") }
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            Log.e("OnResult", uri.toString())
            imageUri.value = uri
            if (uri != null) {
                vm.addPhoto(uri)
            }
        })
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
            .padding(2.dp)
            .fillMaxSize()
    ) {
        LazyRow(modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
            content = {
                items(vm.photos) { url ->
                    PhotoItem(url, onDelete = {
                        vm.deletePhoto(url!!)
                    }, onAdd = {
                        imagePicker.launch("image/*")

                    })
                }
            })
        NewAdTextField(
            nameText, modifier = Modifier
                .padding(top = 6.dp, start = 2.dp, end = 2.dp)
                .fillMaxWidth(), "Name of product", KeyboardType.Text,
            onValueChanged = {vm.setName(nameText.value)}
        )
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            NewAdTextField(
                priceText, modifier = Modifier
                    .padding(top = 6.dp, start = 2.dp, end = 2.dp)
                    .weight(1f), "Price", KeyboardType.Number,
                onValueChanged = {vm.setPrice(priceText.value)}
            )
            Text(
                text = "\$",
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 6.dp, start = 4.dp),
                textAlign = TextAlign.Left,
                color = Color.DarkGray,
                fontSize = 28.sp
            )
        }
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            NewAdTextField(
                cityText, modifier = Modifier
                    .padding(top = 6.dp, start = 2.dp, end = 2.dp)
                    .weight(1f), "City", KeyboardType.Text,
                onValueChanged = {vm.setCity(cityText.value)}
            )
            Spacer(modifier = Modifier.weight(1f))
        }
        CustomDropdownMenu(
            modifier = Modifier
                .padding(start = 2.5.dp, end = 2.dp, top = 6.dp)
                .fillMaxWidth(),
            "Select category",
            options = Constants.CATEGORIES,
            onItemSelected = { vm.setSelectedCategory(it) })
        val scope = rememberCoroutineScope()
        val filters = remember {
            mutableStateOf(mapOf<String, ArrayList<String>>())
        }
        Log.e("FILTERS", filters.toString())
        SideEffect {
            scope.launch {
                vm.filtersForCategory.collect {
                    filters.value = it
                }
            }
        }

        Column(modifier = Modifier.padding(top = 15.dp, bottom = 5.dp, start = 4.dp, end = 4.dp)) {
            for (key in filters.value.map { it.key }) {
                val options = filters.value.get(key)
                options?.toList()?.let {
                    CustomDropdownMenuStr(modifier = Modifier
                        .padding(start = 2.5.dp, end = 2.dp, top = 6.dp)
                        .fillMaxWidth(), label = key, options = it, onItemSelected = { item ->
                        vm.addSelectedFilter(key, item)
                    }
                    )
                }
            }
        }
        val description = remember {
            mutableStateOf("")
        }
        NewAdTextField(
            state = description,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 3.dp, end = 3.dp)
                .height(100.dp),
            label = "Description",
            keyboardType = KeyboardType.Text,
            fontWeight = FontWeight.Medium,
            hint = "Shortly describe the product",
            onValueChanged = {vm.setDescription(description.value)}
        )

        Button(
            onClick = {
                      scope.launch{
                          vm.postAdvertisement()
                      }
                if(vm.validateInput()){
                    vm.clear()
                    navController.navigate(Destination.Home.route)
                }

            },
            shape = (RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFABACAB)
            ), modifier = Modifier.fillMaxWidth().padding(start = 3.dp, end = 3.dp, top = 10.dp, bottom = 5.dp)
        ) {
            Text(text = "Post advertisement", Modifier.padding(top = 8.dp, bottom =8.dp), fontSize = 20.sp)
        }


    }

}

@Composable
fun PhotoItem(url: Uri?, onDelete: () -> Unit, onAdd: () -> Unit) {
    Box(
        modifier = Modifier
            .size(130.dp)
            .padding(2.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.LightGray)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
            .aspectRatio(1f)

    ) {
        if (url == null) {
            Image(
                painter = painterResource(id = R.drawable.add_photo),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
                    .padding(2.dp)
                    .clickable {
                        onAdd()
                    }
            )
        } else {
            val painter = rememberAsyncImagePainter(model = url)
            Image(
                painter, contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
                    .clip(RoundedCornerShape(8.dp))
            )
            IconButton(
                onClick = {
                    Log.e("OnDelete", "IconButton")
                    onDelete()
                },
                modifier = Modifier
                    .size(18.dp)
                    .align(Alignment.TopEnd)
                    .padding(2.dp)
            ) {
                Icon(painterResource(id = R.drawable.cancel), contentDescription = null)
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAdTextField(
    state: MutableState<String>,
    modifier: Modifier,
    label: String,
    keyboardType: KeyboardType,
    fontWeight: FontWeight = FontWeight.Normal,
    hint: String = "",
    onValueChanged: ()->Unit = {}
) {
    OutlinedTextField(
        value = state.value,
        onValueChange = {
            state.value = it
            onValueChanged()
                        },
        modifier = modifier,
        placeholder = { Text(text = hint) },
        label = { Text(text = label, fontWeight = fontWeight) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            cursorColor = Color.DarkGray,
            focusedBorderColor = Color.DarkGray,
            focusedLabelColor = Color.DarkGray
        )
    )
}