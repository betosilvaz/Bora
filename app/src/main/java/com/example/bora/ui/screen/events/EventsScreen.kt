package com.example.bora.ui.screen.events

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class EventItem(
    val id: Int,
    val title: String,
    val date: String,
    val location: String,
    val description: String,
    val gradientColors: List<Color> // Simula as cores da imagem do evento
)

val sampleEvents = listOf(
    EventItem(
        id = 1,
        title = "Neon Night Party",
        date = "HOJE, 23:00",
        location = "Club Vibe • Centro",
        description = "A melhor festa eletrônica da cidade com DJs internacionais. Vista roupas brancas ou neon e venha brilhar na pista!",
        gradientColors = listOf(Color(0xFF6200EA), Color(0xFFE91E63)) // Roxo para Rosa
    ),
    EventItem(
        id = 2,
        title = "Samba de Raiz",
        date = "SÁB, 16:00",
        location = "Bar do Zeca • Zona Sul",
        description = "Roda de samba tradicional, cerveja gelada e feijoada. Chegue cedo para garantir sua mesa. Couvert artístico: R$ 20.",
        gradientColors = listOf(Color(0xFFFF9800), Color(0xFFF44336)) // Laranja para Vermelho
    ),
    EventItem(
        id = 3,
        title = "Indie Rock Festival",
        date = "12 DE JUN, 20:00",
        location = "Arena Show",
        description = "Três bandas covers tocando Arctic Monkeys, The Strokes e muito mais. Promoção de chopp até as 22h.",
        gradientColors = listOf(Color(0xFF0F2027), Color(0xFF203A43), Color(0xFF2C5364)) // Tons escuros alternativos
    )
)

@Composable
fun EventsScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 48.dp, bottom = 160.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(
                    text = "Seus Rolês",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 28.sp,
                    modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp),
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
            items(sampleEvents) { event ->
                EventCard(event = event)
            }
        }
    }
}

@Composable
fun EventCard(event: EventItem) {
    ElevatedCard(modifier = Modifier.padding(horizontal = 16.dp)) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // 1. Imagem
            Surface(modifier = Modifier.height(200.dp).fillMaxWidth(), color = MaterialTheme.colorScheme.primary) {

            }

            // 2. Informações
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = event.title, fontWeight = FontWeight.Bold, fontSize = 32.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = event.description, maxLines = 2, overflow = TextOverflow.Ellipsis)
                Spacer(modifier = Modifier.height(16.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Icon(imageVector = Icons.Outlined.AccessTime, contentDescription = null)
                    Text(text = event.date)
                }
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Icon(imageVector = Icons.Outlined.LocationOn, contentDescription = null)
                    Text(text = event.location)
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider()
                Spacer(modifier = Modifier.height(16.dp))
                Row() {
                    // 1. Avatares


                    // 2. Número de pessoas


                }
            }
        }
    }
}