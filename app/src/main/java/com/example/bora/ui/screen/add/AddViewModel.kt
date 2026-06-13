package com.example.bora.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bora.localStorage.LocalStorage
import com.example.bora.model.Event
import com.example.bora.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

data class AddUiState(
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val date: String = "",
    val hasGuestLimit: Boolean = false,
    val guestLimit: String = "",
    val isPublic: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
) {
    val isValid: Boolean get() =
        name.isNotBlank() && description.isNotBlank() && address.isNotBlank() && date.isNotBlank()
}

class AddViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    fun updateName(name: String) { _uiState.update { it.copy(name = name) } }
    fun updateDescription(description: String) { _uiState.update { it.copy(description = description) } }
    fun updateAddress(address: String) { _uiState.update { it.copy(address = address) } }
    fun updateDate(date: String) { _uiState.update { it.copy(date = date) } }
    fun updateHasGuestLimit(value: Boolean) { _uiState.update { it.copy(hasGuestLimit = value, guestLimit = if (!value) "" else it.guestLimit) } }
    fun updateGuestLimit(value: String) { _uiState.update { it.copy(guestLimit = value.filter { c -> c.isDigit() }) } }
    fun updateIsPublic(value: Boolean) { _uiState.update { it.copy(isPublic = value) } }

    fun save() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val state = _uiState.value
            val event = Event(
                id = UUID.randomUUID().toString(),
                hostId = LocalStorage.getItem("userId") ?: "unknown",
                name = state.name,
                description = state.description,
                address = state.address,
                date = state.date,
                hasGuestLimit = state.hasGuestLimit,
                guestLimit = state.guestLimit.toIntOrNull() ?: 0,
                isPublic = state.isPublic
            )
            EventRepository.all().add(event)
            _uiState.update { it.copy(isSuccess = true, isLoading = false) }
        }
    }
}
