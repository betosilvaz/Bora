package com.example.bora.ui.screen.login

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bora.ui.theme.BoraTheme

@Composable
fun LoginScreen(
    state: LoginUiState,
    onClickLogin: () -> Unit,
    onClickSignup: () -> Unit,
    onClickForgotPassword: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 48.dp)
        ) {
            Text(
                text = "Entrar",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(top = 42.dp, bottom = 120.dp)
            ) {
                FieldLabel("E-mail")
                Spacer(modifier = Modifier.height(4.dp))
                Input(
                    placeholder = "seuemail@exemplo.com",
                    value = state.email,
                    onChange = { viewModel.updateEmail(it) },
                    leadingIcon = Icons.Outlined.Email,
                    keyboardType = KeyboardType.Email
                )
                Spacer(modifier = Modifier.height(14.dp))
                FieldLabel("Senha")
                Spacer(modifier = Modifier.height(4.dp))
                PasswordInput(
                    placeholder = "••••••••",
                    value = state.password,
                    isVisible = state.isVisible,
                    onChange = { viewModel.updatePassword(it) },
                    onVisibilityChange = { viewModel.toggleVisibility() }
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onClickForgotPassword) {
                        Text(
                            text = "Esqueci minha senha",
                            fontSize = 12.sp,
                            textDecoration = TextDecoration.Underline,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        )
                    }
                }
                if (state.errorMessage.isNotBlank()) {
                    Text(color = Color.Red, text = state.errorMessage, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(10.dp))
                }
                Spacer(modifier = Modifier.height(4.dp))
                Button(
                    onClick = onClickLogin,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    enabled = state.isLoginValid,
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(text = "Entrar", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedButton(
                    onClick = onClickSignup,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(text = "Cadastrar-se", fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
fun FieldLabel(text: String) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Medium,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
        letterSpacing = 0.01.em
    )
}

@Composable
fun Input(
    placeholder: String,
    value: String,
    onChange: (String) -> Unit,
    leadingIcon: ImageVector,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        leadingIcon = { Icon(imageVector = leadingIcon, contentDescription = null) },
        shape = RoundedCornerShape(4.dp),
        placeholder = { Text(text = placeholder) },
        value = value,
        onValueChange = onChange,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun PasswordInput(
    placeholder: String,
    value: String,
    isVisible: Boolean,
    onChange: (String) -> Unit,
    onVisibilityChange: () -> Unit
) {
    OutlinedTextField(
        leadingIcon = { Icon(imageVector = Icons.Outlined.Lock, contentDescription = null) },
        trailingIcon = {
            val icon = if (isVisible) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff
            IconButton(onClick = onVisibilityChange) {
                Icon(imageVector = icon, contentDescription = null)
            }
        },
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
        shape = RoundedCornerShape(4.dp),
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
        LoginScreen(LoginUiState(), onClickLogin = {}, onClickSignup = {}, onClickForgotPassword = { })
    }
}
