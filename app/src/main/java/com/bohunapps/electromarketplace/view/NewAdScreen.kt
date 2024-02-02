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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.bohunapps.electromarketplace.R
import com.bohunapps.electromarketplace.viewmodel.NewAdViewModel

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun NewAdScreen(
    navController: NavHostController,
    vm: NewAdViewModel,
    paddingValues: PaddingValues,
) {
    val imageUri = remember {
        mutableStateOf<Uri?>(null)
    }
    val ctx = LocalContext.current
    val imagePicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri->
            Log.e("OnResult", uri.toString())
            imageUri.value = uri
            if (uri != null) {
                vm.addPhoto(uri)
            }
        })

    /*val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture() ,
        onResult = {success->
            if(success&&imageUri.value!=null){
                vm.addPhoto(imageUri.value!!)
            }
        })*/
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .padding(paddingValues)
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

    }

}

@Composable
fun PhotoItem(url: Uri?, onDelete: () -> Unit, onAdd: ()->Unit) {

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
