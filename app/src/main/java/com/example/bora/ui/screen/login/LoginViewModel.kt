package com.example.bora.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bora.service.AuthenticationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isVisible: Boolean = false,
    val errorMessage: String = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
) {
    val isLoginValid: Boolean get() {
        val isEverythingFilled = email.isNotBlank() && password.isNotBlank()
        return isEverythingFilled
    }
}

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateEmail(email: String) {
        _uiState.update { it.copy(email =  email) }
    }

    fun updatePassword(password: String) {
        _uiState.update { it.copy(password = password) }
    }

    fun toggleVisibility() {
        _uiState.update { it.copy(isVisible = !it.isVisible) }
    }

    fun setErrorMessage(message: String) {
        _uiState.update { it.copy(errorMessage = message) }
    }

    fun login() {
        viewModelScope.launch {
           try {
               _uiState.update { it.copy(isLoading = true) }

               val success = AuthenticationService.login(_uiState.value.email, _uiState.value.password)

               if (success) {
                   _uiState.update { it.copy(isSuccess = true, isLoading = false, errorMessage = "") }
               } else {
                   _uiState.update { it.copy(errorMessage = "Credenciais inválidas.", isLoading = false) }
               }

           } catch (_: Exception) {
               _uiState.update { it.copy(isLoading = false, errorMessage = "Erro de conexão") }
           }
        }
    }
}