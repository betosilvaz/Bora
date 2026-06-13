package com.example.bora.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bora.localStorage.LocalStorage
import com.example.bora.model.Event
import com.example.bora.model.User
import com.example.bora.repository.EventRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DetailsUiState(
    val event: Event? = null,
    val participantCount: Int = 0,
    val isHost: Boolean = false,
    val isParticipant: Boolean = false,
)

class DetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    fun loadEvent(eventId: String) {
        viewModelScope.launch {
            val event = EventRepository.getById(eventId) ?: return@launch
            val userId = LocalStorage.getItem("userId") ?: ""
            _uiState.value = DetailsUiState(
                event = event,
                participantCount = event.participants.size,
                isHost = event.hostId == userId,
                isParticipant = event.participants.any { it.id == userId }
            )
        }
    }

    fun participate() {
        val state = _uiState.value
        val event = state.event ?: return
        val userId = LocalStorage.getItem("userId") ?: return
        val username = LocalStorage.getItem("username") ?: "Usuário"

        val user = User(
            id = userId,
            username = username,
            email = "",
            passwordHash = ""
        )
        event.participants.add(user)

        _uiState.value = state.copy(
            participantCount = event.participants.size,
            isParticipant = true
        )
    }
}
