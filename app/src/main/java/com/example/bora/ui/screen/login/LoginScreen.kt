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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bora.ui.theme.BoraTheme

@Composable
fun LoginScreen(
    onClickLogin: () -> Unit,
    onClickSignup: () -> Unit,
    onClickForgotPassword: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    // 1. Criamos o estado de rolagem da tela
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(), // 2. O Surface empurra tudo para a área segura (barras e teclado)
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // 3. Habilitamos a rolagem no container principal
        ) {

            // ── Hero ──────────────────────────────────────────────
            Column(
                modifier = Modifier
                    // 4. Trocamos o .weight(1f) por um padding no topo.
                    // Isso mantém o texto empurrado para baixo sem quebrar a rolagem!
                    .padding(horizontal = 32.dp)
                    .padding(top = 80.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Bem-vindo de volta",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.14.em,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = "Bora!",
                    fontSize = 72.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-2).sp,
                    lineHeight = 72.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Encontre eventos, conecte pessoas,\nviva a cidade.",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Light,
                    lineHeight = 21.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }

            // ── Form sheet ────────────────────────────────────────
            HorizontalDivider(thickness = 0.5.dp)
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(top = 28.dp, bottom = 36.dp)
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
                Spacer(modifier = Modifier.height(18.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp)
                    Text(
                        text = "ou",
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp)
                }
                Spacer(modifier = Modifier.height(14.dp))
                OutlinedButton(
                    onClick = onClickSignup,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(text = "Cadastrar-se", fontSize = 15.sp, fontWeight = FontWeight.Normal)
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
        LoginScreen(onClickLogin = {}, onClickSignup = {}, onClickForgotPassword = {})
    }
}