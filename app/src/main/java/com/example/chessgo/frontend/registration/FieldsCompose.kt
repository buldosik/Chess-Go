package com.example.chessgo.frontend.registration

import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun EmailField(offset: Float = 0f, email: String, onValueChange: (String) -> Unit) {
    OutlinedTextField(
        value = email,
        onValueChange = { updated -> onValueChange(updated) },
        label = { Text("Email") },
        modifier = Modifier
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .offset (y = offset.dp),
    )
}

@Composable
fun PasswordField(offset: Float = 0f, password: String, onPasswordChange: (String) -> Unit) {
    var passwordVisible: Boolean by remember { mutableStateOf(false) }
    OutlinedTextField(
        modifier = Modifier.offset(y = offset.dp),
        value = password,
        label = { Text(text = "Password") },
        trailingIcon = {
            val image = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility
            val description = null
            IconButton(onClick = {
                passwordVisible = !passwordVisible
            }) {
                Icon(
                    imageVector = image,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground)
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        onValueChange = {updatedPassword -> onPasswordChange(updatedPassword) },
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
        ),
    )
}