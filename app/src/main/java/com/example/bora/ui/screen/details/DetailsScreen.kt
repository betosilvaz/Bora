package com.example.bora.ui.screen.details

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bora.model.EVENT_DATE_FORMATTER
import com.example.bora.ui.screen.login.FieldLabel

@Composable
fun DetailsScreen(
    state: DetailsUiState,
    onClickBack: () -> Unit,
    onParticipate: () -> Unit,
) {
    val event = state.event ?: return
    val scrollState = rememberScrollState()

    val canParticipate = !state.isHost && !state.isParticipant
    val buttonText = when {
        state.isHost -> "Você é o anfitrião"
        state.isParticipant -> "Já está participando"
        else -> "Participar"
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onClickBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
            }

            Text(
                text = event.name,
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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.AccessTime,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = event.date.format(EVENT_DATE_FORMATTER),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Outlined.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = event.address,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                FieldLabel("Descrição")
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = event.description,
                    fontSize = 14.sp,
                    lineHeight = 21.sp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )

                event.extraSection.forEach { section ->
                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(thickness = 0.5.dp)
                    Spacer(modifier = Modifier.height(14.dp))
                    FieldLabel(section.title)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = section.body,
                        fontSize = 14.sp,
                        lineHeight = 21.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "${state.participantCount} participante(s)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (event.hasGuestLimit) {
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Limite de ${event.guestLimit} convidados",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.45f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = onParticipate,
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    enabled = canParticipate,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(text = buttonText, fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
