package com.bohunapps.electromarketplace.view

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bohunapps.electromarketplace.Destination
import com.bohunapps.electromarketplace.R
import com.bohunapps.electromarketplace.Util.OutlinedBox
import com.bohunapps.electromarketplace.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInScreen(navController: NavController, vm: AuthViewModel) {

    val emailText = remember {
        mutableStateOf("")
    }
    val passwordText = remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo), contentDescription = "logo",
            modifier = Modifier
                .size(220.dp)
                .padding(top = 30.dp)
        )
        OutlinedBox(
            state = emailText,
            marTop = 20,
            label = "Email",
            keyboardType = KeyboardType.Email
        )
        OutlinedBox(
            state = passwordText,
            marTop = 10,
            label = "Password",
            keyboardType = KeyboardType.Password
        )
        val scope = rememberCoroutineScope()
        val ctx = LocalContext.current

        Button(
            onClick = {
                vm.signUserIn(
                    emailText.value,
                    passwordText.value
                )
                scope.launch {
                    vm.signInFlow.collect {
                        if (it?.data != null) {
                            navController.navigate(Destination.Home.route)
                        }
                    }
                }
            },
            modifier = Modifier
                .width(220.dp)
                .padding(top = 60.dp),
            shape = (RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3C3A3A)
            )
        ) {
            Text(text = "Sign in", modifier = Modifier.padding(6.dp), fontSize = 18.sp)
        }

        Text(
            text = "Do not have an account?\n Go to Sign Up!",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            color = Color(0xFF3C3A3A),
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable {
                    navController.navigate(Destination.SignUp.route) {
                        popUpTo(Destination.SignUp.route)
                        launchSingleTop = true
                    }
                }
        )
    }

}