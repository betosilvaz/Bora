package com.example.bora.ui.screen.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bora.Response
import com.example.bora.dto.CreateUserDto
import com.example.bora.localStorage.LocalStorage
import com.example.bora.model.User
import com.example.bora.repository.UserRepository
import com.example.bora.service.AuthenticationService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class SignupUiState(
    val username: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isVisible: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
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

    fun signup() {
        viewModelScope.launch {
            try {
                val user: CreateUserDto = CreateUserDto(
                    _uiState.value.username,
                    _uiState.value.email,
                    _uiState.value.password,
                    _uiState.value.confirmPassword
                )

                _uiState.update { it.copy(isLoading = true) }

                val response: Response = AuthenticationService.signup(user)

                if (response.status == "SUCCESS") {
                    val createdUser: User? = UserRepository.getByUsername(user.username)
                    LocalStorage.setItem("username", createdUser!!.username)
                    LocalStorage.setItem("userId", createdUser!!.id)
                    _uiState.update { it.copy(isLoading = false, isSuccess = true) }
                } else {
                    _uiState.update { it.copy(isLoading = false, errorMessage = response.message) }
                }

            } catch(_: Exception) {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Erro de conexão") }
            }
        }
    }

}