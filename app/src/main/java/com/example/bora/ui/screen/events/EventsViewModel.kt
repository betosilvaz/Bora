package com.example.bora.ui.screen.events

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

data class EventsUiState(
    val selectedFilter: String = "Anfitrião",
    val events: List<Event> = emptyList(),
)

class EventsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(EventsUiState())
    val uiState: StateFlow<EventsUiState> = _uiState.asStateFlow()

    val userId: String = LocalStorage.getItem("userId") ?: ""

    init {
        loadEvents()
    }

    private fun loadEvents() {
        viewModelScope.launch {
            val events = EventRepository.all()
            _uiState.update { it.copy(events = events) }
        }
    }

    fun selectFilter(filter: String) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }
}
