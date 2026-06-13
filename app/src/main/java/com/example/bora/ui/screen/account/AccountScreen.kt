package com.example.bora.ui.screen.account

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.bora.ui.screen.login.Input
import com.example.bora.ui.screen.login.PasswordInput
import com.example.bora.ui.screen.login.FieldLabel

@Composable
fun AccountScreen(
    state: AccountUiState,
    onToggleEditMode: () -> Unit,
    onSaveClick: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: (PasswordType) -> Unit
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
        ) {
            // Cabeçalho
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(top = 80.dp, bottom = 40.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = "CONTA",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 0.14.em,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f),
                    modifier = Modifier.padding(bottom = 6.dp)
                )
                Text(
                    text = if (state.isEditing) "Editar Perfil" else "Seu Perfil",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-2).sp,
                    lineHeight = 48.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            HorizontalDivider(thickness = 0.5.dp)

            // Conteúdo (Alterna entre Visualização e Edição)
            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(top = 28.dp, bottom = 36.dp)
            ) {
                if (state.isEditing) {
                    EditProfileContent(
                        state = state,
                        onCancelClick = onToggleEditMode,
                        onSaveClick = onSaveClick,
                        onUsernameChange = onUsernameChange,
                        onEmailChange = onEmailChange,
                        onCurrentPasswordChange = onCurrentPasswordChange,
                        onNewPasswordChange = onNewPasswordChange,
                        onConfirmPasswordChange = onConfirmPasswordChange,
                        onTogglePasswordVisibility = onTogglePasswordVisibility
                    )
                } else {
                    ViewProfileContent(
                        state = state,
                        onEditClick = onToggleEditMode
                    )
                }
            }
        }
    }
}

@Composable
private fun ViewProfileContent(
    state: AccountUiState,
    onEditClick: () -> Unit
) {
    Column {
        FieldLabel("Nome de Usuário")
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = state.username,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(24.dp))

        FieldLabel("E-mail")
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = state.email,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(48.dp))

        OutlinedButton(
            onClick = onEditClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(4.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Icon(Icons.Outlined.Edit, contentDescription = null, modifier = Modifier.size(20.dp))
                Text(text = "Editar Dados e Senha", fontSize = 15.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}

@Composable
private fun EditProfileContent(
    state: AccountUiState,
    onCancelClick: () -> Unit,
    onSaveClick: () -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onCurrentPasswordChange: (String) -> Unit,
    onNewPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onTogglePasswordVisibility: (PasswordType) -> Unit
) {
    Column {
        // Dados Pessoais
        Text(
            text = "Dados Pessoais",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            letterSpacing = 0.01.em
        )
        Spacer(modifier = Modifier.height(16.dp))

        FieldLabel("Nome de Usuário")
        Spacer(modifier = Modifier.height(4.dp))
        Input(
            placeholder = "Seu usuário",
            value = state.usernameInput,
            onChange = onUsernameChange,
            leadingIcon = Icons.Outlined.PersonOutline
        )

        Spacer(modifier = Modifier.height(14.dp))

        FieldLabel("E-mail")
        Spacer(modifier = Modifier.height(4.dp))
        Input(
            placeholder = "seuemail@exemplo.com",
            value = state.emailInput,
            onChange = onEmailChange,
            leadingIcon = Icons.Outlined.Email,
            keyboardType = KeyboardType.Email
        )

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(thickness = 0.5.dp)
        Spacer(modifier = Modifier.height(24.dp))

        // Segurança
        Text(
            text = "Segurança (Alterar Senha)",
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            letterSpacing = 0.01.em
        )
        Spacer(modifier = Modifier.height(16.dp))

        FieldLabel("Senha Atual (Obrigatório para salvar)")
        Spacer(modifier = Modifier.height(4.dp))
        PasswordInput(
            placeholder = "••••••••",
            value = state.currentPasswordInput,
            isVisible = state.isCurrentPasswordVisible,
            onChange = onCurrentPasswordChange,
            onVisibilityChange = { onTogglePasswordVisibility(PasswordType.CURRENT) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        FieldLabel("Nova Senha")
        Spacer(modifier = Modifier.height(4.dp))
        PasswordInput(
            placeholder = "••••••••",
            value = state.newPasswordInput,
            isVisible = state.isNewPasswordVisible,
            onChange = onNewPasswordChange,
            onVisibilityChange = { onTogglePasswordVisibility(PasswordType.NEW) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        FieldLabel("Confirmar Nova Senha")
        Spacer(modifier = Modifier.height(4.dp))
        PasswordInput(
            placeholder = "••••••••",
            value = state.confirmPasswordInput,
            isVisible = state.isConfirmPasswordVisible,
            onChange = onConfirmPasswordChange,
            onVisibilityChange = { onTogglePasswordVisibility(PasswordType.CONFIRM) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botões de Ação
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(4.dp),
            enabled = state.currentPasswordInput.isNotBlank() // Exemplo de validação básica
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Check, contentDescription = null, modifier = Modifier.size(20.dp))
                Text(text = "Salvar Alterações", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(46.dp),
            shape = RoundedCornerShape(4.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Close, contentDescription = null, modifier = Modifier.size(20.dp))
                Text(text = "Cancelar", fontSize = 15.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}