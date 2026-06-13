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
import androidx.compose.ui.unit.sp
import com.example.bora.ui.screen.login.FieldLabel
import com.example.bora.ui.screen.login.Input
import com.example.bora.ui.screen.login.PasswordInput

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
                .padding(top = 48.dp)
        ) {
            Text(
                text = if (state.isEditing) "Editar Perfil" else "Seu Perfil",
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
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(20.dp))

        FieldLabel("E-mail")
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = state.email,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(36.dp))

        OutlinedButton(
            onClick = onEditClick,
            modifier = Modifier.fillMaxWidth().height(46.dp),
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

        Spacer(modifier = Modifier.height(24.dp))

        FieldLabel("Senha Atual")
        Spacer(modifier = Modifier.height(4.dp))
        PasswordInput(
            placeholder = "Senha atual (obrigatória para salvar)",
            value = state.currentPasswordInput,
            isVisible = state.isCurrentPasswordVisible,
            onChange = onCurrentPasswordChange,
            onVisibilityChange = { onTogglePasswordVisibility(PasswordType.CURRENT) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        FieldLabel("Nova Senha")
        Spacer(modifier = Modifier.height(4.dp))
        PasswordInput(
            placeholder = "Nova senha",
            value = state.newPasswordInput,
            isVisible = state.isNewPasswordVisible,
            onChange = onNewPasswordChange,
            onVisibilityChange = { onTogglePasswordVisibility(PasswordType.NEW) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        FieldLabel("Confirmar Nova Senha")
        Spacer(modifier = Modifier.height(4.dp))
        PasswordInput(
            placeholder = "Confirme a nova senha",
            value = state.confirmPasswordInput,
            isVisible = state.isConfirmPasswordVisible,
            onChange = onConfirmPasswordChange,
            onVisibilityChange = { onTogglePasswordVisibility(PasswordType.CONFIRM) }
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth().height(46.dp),
            shape = RoundedCornerShape(4.dp),
            enabled = state.currentPasswordInput.isNotBlank()
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Check, contentDescription = null, modifier = Modifier.size(20.dp))
                Text(text = "Salvar Alterações", fontSize = 15.sp, fontWeight = FontWeight.Medium, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = onCancelClick,
            modifier = Modifier.fillMaxWidth().height(46.dp),
            shape = RoundedCornerShape(4.dp),
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Close, contentDescription = null, modifier = Modifier.size(20.dp))
                Text(text = "Cancelar", fontSize = 15.sp, modifier = Modifier.padding(start = 8.dp))
            }
        }
    }
}
