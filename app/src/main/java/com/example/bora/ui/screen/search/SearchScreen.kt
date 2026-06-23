package com.example.bora.ui.screen.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

import com.example.bora.model.EVENT_DATE_FORMATTER
import com.example.bora.model.Event
import com.example.bora.ui.screen.login.FieldLabel

@Composable
fun SearchScreen(
    state: SearchUiState,
    onClickEvent: (String) -> Unit = {},
    viewModel: SearchViewModel,
) {
    val scrollState = rememberScrollState()

    val categories = listOf("Todos", "Festas", "Shows", "Cultural", "Esportes", "Gastronomia")

    val filteredEvents = if (state.searchQuery.isBlank()) {
        state.events
    } else {
        state.events.filter {
            it.name.contains(state.searchQuery, ignoreCase = true) ||
            it.address.contains(state.searchQuery, ignoreCase = true)
        }
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
                text = "Explorar",
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
                FieldLabel("Buscar")
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.searchQuery,
                    onValueChange = { viewModel.updateSearchQuery(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Buscar rolês, locais, artistas...") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = null)
                    },
                    shape = RoundedCornerShape(4.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                FieldLabel("Categorias")
                Spacer(modifier = Modifier.height(4.dp))
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(categories) { category ->
                        CategoryChip(
                            text = category,
                            isSelected = category == state.selectedCategory,
                            onClick = { viewModel.selectCategory(category) }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "Em Alta",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    letterSpacing = 0.01.em
                )

                Spacer(modifier = Modifier.height(16.dp))

                filteredEvents.forEach { event ->
                    ExploreEventCard(
                        event = event,
                        onClick = { onClickEvent(event.id) }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun CategoryChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    val backgroundColor = if (isSelected) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.surfaceVariant
    val textColor = if (isSelected) MaterialTheme.colorScheme.onPrimary
        else MaterialTheme.colorScheme.onSurfaceVariant

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundColor)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    }
}

@Composable
fun ExploreEventCard(event: Event, onClick: (() -> Unit)? = null) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null) { onClick?.invoke() },
        shape = RoundedCornerShape(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(4.dp)),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = event.name.take(1).uppercase(),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = event.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.date.format(EVENT_DATE_FORMATTER),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = event.address,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                    fontSize = 12.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
