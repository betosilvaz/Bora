package com.example.bora.ui.screen.login

import android.content.res.Configuration
import android.view.View
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bora.ui.theme.BoraTheme

@Composable
fun LoginScreen(onClickLogin: () -> Unit, onClickSignup: () -> Unit, viewModel: LoginViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val onChangeUsername = { value: String -> viewModel.updateUsername(value) }
    val onChangePassword = { value: String -> viewModel.updatePassword(value) }
    val onToggleVisibility = { viewModel.toggleVisibility() }

    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(32.dp)) {
            Text(
                fontSize = 36.sp,
                fontWeight = FontWeight.ExtraBold,
                text = "Bora!"
            )
            Spacer(modifier = Modifier.height(16.dp))
            Input("johndoe", state.username, onChangeUsername, Icons.Outlined.Person)
            Spacer(modifier = Modifier.height(16.dp))
            PasswordInput("••••••••", state.password, state.isVisible, onChangePassword, onToggleVisibility)
            Spacer(modifier = Modifier.height(16.dp))
            if (state.errorMessage.isNotBlank()) {
                Text(
                    color = Color.Red,
                    text = state.errorMessage
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            Button(
                onClick = onClickLogin,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isLoginValid,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Entrar")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f))
                Text(text = "ou", modifier = Modifier.padding(horizontal = 8.dp))
                HorizontalDivider(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onClickSignup,
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Cadastrar-se")
            }
        }
    }
}

@Composable
fun Input(placeholder: String, value: String, onChange: (String) -> Unit, leadingIcon: ImageVector) {
    OutlinedTextField(
        leadingIcon = { Icon(imageVector = leadingIcon, null) },
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(text = placeholder) },
        value = value,
        onValueChange = onChange,
        singleLine = true,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordInput(placeholder: String, value: String, isVisible: Boolean, onChange: (String) -> Unit, onVisibilityChange: () -> Unit) {
    val leadingIcon = Icons.Outlined.Lock
    OutlinedTextField(
        leadingIcon = { Icon(imageVector = leadingIcon, null) },
        trailingIcon = {
            val icon = if (isVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            IconButton(onClick = onVisibilityChange) {
                Icon(imageVector = icon, null)
            }
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(text = placeholder) },
        value = value,
        onValueChange = onChange,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
    )
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun LoginScreenPreview() {
    BoraTheme {
        LoginScreen(onClickLogin = {  }, onClickSignup = { })
    }
}