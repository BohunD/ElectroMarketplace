package com.bohunapps.electromarketplace.view

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
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bohunapps.electromarketplace.R
import com.bohunapps.electromarketplace.Util.OutlinedBox

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun SignUpScreen() {
    val emailText = remember {
        mutableStateOf("")
    }
    val passwordText = remember {
        mutableStateOf("")
    }
    val phoneText = remember {
        mutableStateOf("")
    }
    val usernameText = remember {
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
            state = usernameText,
            marTop = 0,
            label = "Username",
            keyboardType = KeyboardType.Text
        )
        OutlinedBox(
            state = emailText,
            marTop = 10,
            label = "Email",
            keyboardType = KeyboardType.Email
        )
        OutlinedBox(
            state = phoneText,
            marTop = 10,
            label = "Phone number",
            keyboardType = KeyboardType.Phone
        )
        OutlinedBox(
            state = passwordText,
            marTop = 10,
            label = "Password",
            keyboardType = KeyboardType.Password
        )


        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .width(220.dp)
                .padding(top = 60.dp),
            shape = (RoundedCornerShape(4.dp)),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF3C3A3A)
            )
        ) {
            Text(text = "Sign up", modifier = Modifier.padding(6.dp), fontSize = 18.sp)
        }

        Text(
            text = "Already have an account?\n Go to Sign In!",
            fontSize = 14.sp,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.Center,
            color = Color(0xFF3C3A3A),
            modifier = Modifier
                .padding(top = 10.dp)
                .clickable {
                    /*TODO*/
                }
        )
    }
}
