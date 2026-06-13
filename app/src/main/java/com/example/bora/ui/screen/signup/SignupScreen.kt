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
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AlternateEmail
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bora.ui.screen.login.FieldLabel
import com.example.bora.ui.screen.login.Input
import com.example.bora.ui.screen.login.PasswordInput
import com.example.bora.ui.theme.BoraTheme

@Composable
fun SignupScreen(
    state: SignupUiState,
    onSignup: () -> Unit,
    onClickLogin: () -> Unit,
    viewModel: SignupViewModel = viewModel()
) {
    val scrollState = rememberScrollState()

    Surface(
        modifier = Modifier.fillMaxSize().safeDrawingPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 48.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Criar Conta",
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
                FieldLabel("Nome de usuário")
                Spacer(modifier = Modifier.height(4.dp))
                Input(
                    placeholder = "johndoe",
                    value = state.username,
                    onChange = { viewModel.updateUsername(it) },
                    leadingIcon = Icons.Outlined.AlternateEmail
                )

                Spacer(modifier = Modifier.height(14.dp))

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

                FieldLabel("Criar senha")
                Spacer(modifier = Modifier.height(4.dp))
                PasswordInput(
                    placeholder = "••••••••",
                    value = state.password,
                    isVisible = state.isVisible,
                    onChange = { viewModel.updatePassword(it) },
                    onVisibilityChange = { viewModel.toggleVisibility() }
                )

                Spacer(modifier = Modifier.height(14.dp))

                FieldLabel("Confirmar")
                Spacer(modifier = Modifier.height(4.dp))
                PasswordInput(
                    placeholder = "••••••••",
                    value = state.confirmPassword,
                    isVisible = state.isVisible,
                    onChange = { viewModel.updateConfirmPassword(it) },
                    onVisibilityChange = { viewModel.toggleVisibility() }
                )

                Spacer(modifier = Modifier.height(18.dp))

                if (state.errorMessage.isNotBlank()) {
                    Text(text = state.errorMessage, color = Color.Red, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(18.dp))
                }

                Button(
                    onClick = onSignup,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    enabled = state.isSignupValid,
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(text = "Concluir cadastro", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = onClickLogin,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(text = "Já tenho conta", fontSize = 15.sp)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun SignupScreenPreview() {
    BoraTheme {
        SignupScreen(SignupUiState(), onSignup = {}, onClickLogin = {})
    }
}
