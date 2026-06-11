package com.example.bora.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val username: String = "",
    val password: String = "",
    val isVisible: Boolean = false,
    val errorMessage: String = "",
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
) {
    val isLoginValid: Boolean get() {
        val isEverythingFilled = username.isNotBlank() && password.isNotBlank()
        return isEverythingFilled
    }
}

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())

    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun updateUsername(username: String) {
        _uiState.update { it.copy(username =  username) }
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

               val response = LoginService.login(_uiState.value.username, _uiState.value.password)

               if (response.status == ResponseStatus.SUCCESS) {
                   _uiState.update { it.copy(isSuccess = true, isLoading = false, errorMessage = "") }
               } else {
                   _uiState.update { it.copy(errorMessage = response.message, isLoading = false) }
               }

           } catch (_: Exception) {
               _uiState.update { it.copy(isLoading = false, errorMessage = "Erro de conexão") }
           }
        }
    }
}