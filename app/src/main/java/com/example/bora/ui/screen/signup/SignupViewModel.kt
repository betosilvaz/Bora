package com.example.bora.ui.screen.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class SignupUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isVisible: Boolean = false
) {
    val isSignupValid: Boolean get() { return username.isNotBlank() && email.isNotBlank() && password.isNotBlank() && confirmPassword.isNotBlank() }
}

class SignupViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignupUiState())

    val uiState: StateFlow<SignupUiState> = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.update { actual -> actual.copy(username =  username) }
    }

    fun updatePassword(password: String) {
        _uiState.update { actual -> actual.copy(password = password) }
    }

    fun updateEmail(email: String) {
        _uiState.update { actual -> actual.copy(email = email) }
    }

    fun updateConfirmPassword(confirmPassword: String) {
        _uiState.update { actual -> actual.copy(confirmPassword = confirmPassword) }
    }

    fun toggleVisibility() {
        _uiState.update { actual -> actual.copy(isVisible = !actual.isVisible) }
    }

}