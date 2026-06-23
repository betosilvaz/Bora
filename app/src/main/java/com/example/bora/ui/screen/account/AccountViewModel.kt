package com.example.bora.ui.screen.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bora.dto.UpdateUserDto
import com.example.bora.localStorage.LocalStorage
import com.example.bora.model.User
import com.example.bora.repository.UserRepository
import com.example.bora.service.UserService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AccountUiState(
    val isEditing: Boolean = false,
    val id: String = "",

    // Dados atuais para modo de visualização
    val username: String = "",
    val email: String = "",

    // Campos do formulário no modo de edição
    val usernameInput: String = "",
    val emailInput: String = "",
    val currentPasswordInput: String = "",
    val newPasswordInput: String = "",
    val confirmPasswordInput: String = "",

    // Visibilidade das senhas
    val isCurrentPasswordVisible: Boolean = false,
    val isNewPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false
) {

}

enum class PasswordType {
    CURRENT, NEW, CONFIRM
}

class AccountViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AccountUiState())

    val uiState = _uiState.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val username = LocalStorage.getItem("username")
            val user = UserRepository.getByUsername(username ?: "Usuário Desconhecido")

            if (user != null) {
                _uiState.update { currentState ->
                    currentState.copy(
                        id = user.id,
                        username = user.username,
                        email = user.email,
                        usernameInput = user.username,
                        emailInput = user.email
                    )
                }
            }
        }
    }

    fun toggleEditMode() {
        _uiState.update { it.copy(isEditing = !it.isEditing) }
    }

    fun updateUsername(username: String) {
        _uiState.update { it.copy(usernameInput = username) }
    }

    fun updateEmail(email: String) {
        _uiState.update { it.copy(emailInput = email) }
    }

    fun updateCurrentPassword(currentPassword: String) {
        _uiState.update { it.copy(currentPasswordInput = currentPassword) }
    }

    fun updateNewPassword(newPassword: String) {
        _uiState.update { it.copy(newPasswordInput = newPassword) }
    }

    fun updateConfirmNewPassword(confirmNewPassword: String) {
        _uiState.update { it.copy(confirmPasswordInput = confirmNewPassword) }
    }

    fun togglePasswordVisibility(passwordType: PasswordType) {
        when (passwordType) {
            PasswordType.NEW -> {
                _uiState.update { it.copy(isNewPasswordVisible = !it.isNewPasswordVisible) }
            }
            PasswordType.CONFIRM -> {
                _uiState.update { it.copy(isConfirmPasswordVisible = !it.isConfirmPasswordVisible) }
            }
            else -> {
                _uiState.update { it.copy(isCurrentPasswordVisible = !it.isCurrentPasswordVisible) }
            }
        }
    }

    fun save() {
        viewModelScope.launch {
            val id = _uiState.value.id
            val username = _uiState.value.usernameInput
            val email = _uiState.value.emailInput
            val currentPassword = _uiState.value.currentPasswordInput
            val newPassword = _uiState.value.newPasswordInput
            val confirmNewPassword = _uiState.value.confirmPasswordInput

            val dto = UpdateUserDto(id, username, email, currentPassword, newPassword, confirmNewPassword)
            val response = UserService.update(dto)

            if(response.status == "SUCCESS") {
                LocalStorage.setItem("username", username)
                restart()
                loadUserData()
            }
        }
    }

    private fun restart() {
        _uiState.update { it.copy(
            isEditing = false,
            id = "",
            username = "",
            email = "",
            usernameInput = "",
            emailInput = "",
            currentPasswordInput = "",
            newPasswordInput = "",
            confirmPasswordInput = "",
            isCurrentPasswordVisible = false,
            isNewPasswordVisible = false,
            isConfirmPasswordVisible = false
        ) }
    }
}