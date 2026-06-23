package com.example.bora.ui.screen.add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bora.localStorage.LocalStorage
import com.example.bora.model.Event
import com.example.bora.model.ExtraSection
import com.example.bora.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

data class AddUiState(
    val name: String = "",
    val description: String = "",
    val address: String = "",
    val date: LocalDateTime? = null,
    val hasGuestLimit: Boolean = false,
    val guestLimit: String = "",
    val isPublic: Boolean = false,
    val isSuccess: Boolean = false,
    val isLoading: Boolean = false,
    val extraSections: MutableList<ExtraSection> = mutableListOf()
) {
    val isValid: Boolean get() =
        name.isNotBlank() && description.isNotBlank() && address.isNotBlank() && date != null
}

class AddViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AddUiState())
    val uiState: StateFlow<AddUiState> = _uiState.asStateFlow()

    fun updateName(name: String) { _uiState.update { it.copy(name = name) } }
    fun updateDescription(description: String) { _uiState.update { it.copy(description = description) } }
    fun updateAddress(address: String) { _uiState.update { it.copy(address = address) } }
    fun updateDate(date: LocalDateTime) { _uiState.update { it.copy(date = date) } }
    fun updateHasGuestLimit(value: Boolean) { _uiState.update { it.copy(hasGuestLimit = value, guestLimit = if (!value) "" else it.guestLimit) } }
    fun updateGuestLimit(value: String) { _uiState.update { it.copy(guestLimit = value.filter { c -> c.isDigit() }) } }
    fun updateIsPublic(value: Boolean) { _uiState.update { it.copy(isPublic = value) } }

    fun addExtraSection() {
        _uiState.update {
            it.copy(extraSections = (it.extraSections + ExtraSection("", "")).toMutableList())
        }
    }

    fun removeExtraSection(index: Int) {
        _uiState.update {
            val updated = it.extraSections.toMutableList()
            updated.removeAt(index)
            it.copy(extraSections = updated)
        }
    }

    fun updateExtraSectionTitle(index: Int, title: String) {
        _uiState.update {
            val updated = it.extraSections.toMutableList()
            updated[index] = updated[index].copy(title = title)
            it.copy(extraSections = updated)
        }
    }

    fun updateExtraSectionBody(index: Int, body: String) {
        _uiState.update {
            val updated = it.extraSections.toMutableList()
            updated[index] = updated[index].copy(body = body)
            it.copy(extraSections = updated)
        }
    }

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
                date = state.date!!,
                hasGuestLimit = state.hasGuestLimit,
                guestLimit = state.guestLimit.toIntOrNull() ?: 0,
                isPublic = state.isPublic,
                extraSection = state.extraSections
            )
            EventRepository.all().add(event)
            _uiState.update { it.copy(isSuccess = true, isLoading = false) }
        }
    }
}
