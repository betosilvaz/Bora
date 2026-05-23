package com.example.bora.ui.screen.signup

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bora.ui.screen.login.Input
import com.example.bora.ui.screen.login.LoginViewModel
import com.example.bora.ui.screen.login.PasswordInput
import com.example.bora.ui.theme.BoraTheme

@Composable
fun SignupScreen(onSignup: () -> Unit, onClickLogin: () -> Unit, viewModel: SignupViewModel = viewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val onChangeUsername = { value: String -> viewModel.updateUsername(value) }
    val onChangeEmail = { value: String -> viewModel.updateEmail(value) }
    val onChangePassword = { value: String -> viewModel.updatePassword(value) }
    val onChangeConfirmPassword = { value: String -> viewModel.updateConfirmPassword(value) }
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
            Input("johndoe@example.com", state.email, onChangeEmail, Icons.Outlined.Email)
            Spacer(modifier = Modifier.height(16.dp))
            PasswordInput("••••••••", state.password, state.isVisible, onChangePassword, onToggleVisibility)
            Spacer(modifier = Modifier.height(16.dp))
            PasswordInput("••••••••", state.confirmPassword, state.isVisible, onChangeConfirmPassword, onToggleVisibility)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onSignup,
                modifier = Modifier.fillMaxWidth(),
                enabled = state.isSignupValid,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Concluir Cadastro")
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
                onClick = onClickLogin,
                modifier = Modifier.fillMaxWidth(),
                enabled = true,
                shape = RoundedCornerShape(8.dp),
            ) {
                Text(text = "Já Possuo Conta")
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SignupScreenPreview() {
    BoraTheme {
        SignupScreen(onSignup = {}, onClickLogin = {})
    }
}