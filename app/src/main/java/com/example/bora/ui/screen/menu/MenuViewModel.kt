package com.example.bora.ui.screen.menu

import androidx.lifecycle.ViewModel
import com.example.bora.localStorage.LocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class MenuUiState(
    val username: String = "",
)

class MenuViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MenuUiState())

    val uiState = _uiState.asStateFlow()

    fun updateUsername(newName: String) {
        _uiState.update { it.copy(username = newName) }
    }

    fun logout() {
        LocalStorage.removeItem("userId")
        LocalStorage.removeItem("username")
    }

}