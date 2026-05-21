package com.example.bora.ui.screen.login

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isVisible: Boolean = false,
    val errorMessage: String = ""
) {
    val isLoginValid: Boolean get() = username.isNotBlank() && password.isNotBlank()
}

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.update { actual -> actual.copy(username =  username) }
    }

    fun updatePassword(password: String) {
        _uiState.update { actual -> actual.copy(password = password) }
    }

    fun toggleVisibility() {
        _uiState.update { actual -> actual.copy(isVisible = !actual.isVisible) }
    }

    fun setErrorMessage(message: String) {
        _uiState.update { actual -> actual.copy(errorMessage = message) }
    }

}