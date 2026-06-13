package com.example.bora.ui.screen.events

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bora.ui.screen.search.CategoryChip
import com.example.bora.ui.screen.search.ExploreEventCard

@Composable
fun EventsScreen(
    state: EventsUiState,
    onClickEvent: (String) -> Unit = {},
    viewModel: EventsViewModel,
) {
    val scrollState = rememberScrollState()

    val filters = listOf("Anfitrião", "Participante")

    val filteredEvents = when (state.selectedFilter) {
        "Anfitrião" -> state.events.filter { it.hostId == viewModel.userId }
        "Participante" -> state.events.filter { it.participants.any { p -> p.id == viewModel.userId } }
        else -> state.events
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(top = 48.dp)
        ) {
            Text(
                text = "Seus Rolês",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(horizontal = 32.dp)
            )

            Column(
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .padding(top = 42.dp, bottom = 120.dp)
            ) {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filters) { filter ->
                        CategoryChip(
                            text = filter,
                            isSelected = filter == state.selectedFilter,
                            onClick = { viewModel.selectFilter(filter) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                filteredEvents.forEach { event ->
                    ExploreEventCard(
                        event = event,
                        onClick = { onClickEvent(event.id) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                if (filteredEvents.isEmpty()) {
                    Text(
                        text = "Nenhum rolê encontrado.",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                    )
                }
            }
        }
    }
}
