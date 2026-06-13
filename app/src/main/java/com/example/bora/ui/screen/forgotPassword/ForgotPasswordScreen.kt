package com.example.bora.ui.screen.forgotPassword

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
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bora.ui.screen.login.FieldLabel
import com.example.bora.ui.screen.login.Input
import com.example.bora.ui.theme.BoraTheme

@Composable
fun ForgotPasswordScreen(
    onClickBack: () -> Unit,
    onSubmit: () -> Unit,
    viewModel: ForgotPasswordViewModel = viewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
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
        ) {

            // ── Hero ──────────────────────────────────────────────
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(top = 80.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "Recupere seu acesso",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.14.em,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = "Esqueceu?",
                    fontSize = 56.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-2).sp,
                    lineHeight = 56.sp,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Acontece! Digite o e-mail associado à sua\nconta e enviaremos as instruções.",
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
                FieldLabel("E-mail cadastrado")
                Spacer(modifier = Modifier.height(4.dp))
                Input(
                    placeholder = "seuemail@exemplo.com",
                    value = state.email,
                    onChange = { viewModel.updateEmail(it) },
                    leadingIcon = Icons.Outlined.Email,
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onSubmit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    enabled = state.isEmailValid,
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Text(text = "Enviar link de recuperação", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }

                Spacer(modifier = Modifier.height(18.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp)
                    Text(
                        text = "lembrou a senha?",
                        modifier = Modifier.padding(horizontal = 12.dp),
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                    HorizontalDivider(modifier = Modifier.weight(1f), thickness = 0.5.dp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                OutlinedButton(
                    onClick = onClickBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp),
                    shape = RoundedCornerShape(4.dp),
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = null,
                        modifier = Modifier.padding(end = 6.dp)
                    )
                    Text(text = "Voltar para o login", fontSize = 14.sp, fontWeight = FontWeight.Normal)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ForgotPasswordScreenPreview() {
    BoraTheme {
        ForgotPasswordScreen(onClickBack = {}, onSubmit = {})
    }
}