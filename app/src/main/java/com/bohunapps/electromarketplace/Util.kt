package com.bohunapps.electromarketplace

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import com.bohunapps.electromarketplace.model.models.Category
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.suspendCancellableCoroutine
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

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

    suspend fun <T> Task<T>.await(): T {
        return suspendCancellableCoroutine { cont ->
            addOnCompleteListener { task ->
                if (task.exception != null) {
                    cont.resumeWithException(task.exception!!)
                } else {
                    cont.resume(task.result)
                }
            }
        }
    }

    suspend fun <T> Task<T>.awaitSuccess(): T {
        return suspendCancellableCoroutine { cont ->
            addOnSuccessListener {
                cont.resume(it)
            }.addOnFailureListener {
                cont.resumeWithException(Exception(it.message))
            }
        }
    }
    fun getDateTimeFromLong(dateTime: Long?): String {
        dateTime?.let {
            val sdf = SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault())
            return sdf.format(Date(it))
        }
        return "Unknown"
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenuStr(modifier: Modifier, label: String, options: List<String>, onItemSelected: (String) -> Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var option by remember {
        mutableStateOf("")
    }


    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        }
    ) {
        OutlinedTextField(
            value = option,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            label = {
                Text(text = label)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedLabelColor = Color.DarkGray,
                focusedIndicatorColor = Color.DarkGray, textColor = Color.DarkGray
            ),
            modifier = Modifier.fillMaxWidth().menuAnchor()  )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }, modifier = Modifier.background(Color.Transparent).padding(start=2.dp).background(Color(0xFFE9E8E8)).padding(4.dp).fillMaxWidth()
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(text = { Text(text = selectionOption) }, onClick = {
                    option = selectionOption
                    onItemSelected(option)
                    isExpanded = false
                }, modifier = modifier.background(Color(0xFFE7E5E5)).clip(
                    RoundedCornerShape(12.dp)
                ).padding(1.dp))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropdownMenu(modifier: Modifier, label: String, options: List<Category>, onItemSelected: (Category) -> Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var option by remember {
        mutableStateOf(Category("",0, mapOf()))
    }


    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = isExpanded,
        onExpandedChange = {
            isExpanded = !isExpanded
        }
    ) {
        OutlinedTextField(
            value = option.name,
            onValueChange = {},
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            label = {
                Text(text = label)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                containerColor = Color.Transparent,
                focusedLabelColor = Color.DarkGray,
                focusedIndicatorColor = Color.DarkGray, textColor = Color.DarkGray
            ),
            modifier = Modifier.fillMaxWidth().menuAnchor()  )
        ExposedDropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                isExpanded = false
            }, modifier = Modifier.background(Color.Transparent).padding(start=2.dp).background(Color(0xFFE9E8E8)).padding(4.dp).fillMaxWidth()
        ) {
            options.forEach { selectionOption ->
                DropdownMenuItem(text = { Text(text = selectionOption.name) }, onClick = {
                    option = selectionOption
                    onItemSelected(option)
                    isExpanded = false
                }, modifier = modifier.background(Color(0xFFE7E5E5)).clip(
                    RoundedCornerShape(12.dp)
                ).padding(1.dp))
            }
        }
    }
}