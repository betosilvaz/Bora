package com.example.bora.ui.screen.add

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bora.ui.screen.login.FieldLabel

@Composable
fun AddScreen(
    state: AddUiState,
    onSaveSuccess: () -> Unit,
    viewModel: AddViewModel,
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) {
            Toast.makeText(context, "Rolê criado!", Toast.LENGTH_SHORT).show()
            onSaveSuccess()
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
                text = "Novo Rolê",
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
                FieldLabel("Nome do evento")
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.name,
                    onValueChange = { viewModel.updateName(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("ex: Churrasco na Praia") },
                    shape = RoundedCornerShape(4.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                FieldLabel("Descrição")
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { viewModel.updateDescription(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Descreva seu rolê...") },
                    shape = RoundedCornerShape(4.dp),
                    minLines = 3,
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(14.dp))

                FieldLabel("Endereço")
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.address,
                    onValueChange = { viewModel.updateAddress(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("ex: Av. Boa Viagem, 1500 - Boa Viagem, Recife - PE") },
                    shape = RoundedCornerShape(4.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(14.dp))

                FieldLabel("Dia e hora")
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(
                    value = state.date,
                    onValueChange = { viewModel.updateDate(it) },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("dd/MM/yy - HH:mm") },
                    shape = RoundedCornerShape(4.dp),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Limite de convidados",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = state.hasGuestLimit,
                        onCheckedChange = { viewModel.updateHasGuestLimit(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }

                if (state.hasGuestLimit) {
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = state.guestLimit,
                        onValueChange = { viewModel.updateGuestLimit(it) },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Número máximo de convidados") },
                        shape = RoundedCornerShape(4.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Evento público",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = state.isPublic,
                        onCheckedChange = { viewModel.updateIsPublic(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = MaterialTheme.colorScheme.onPrimary,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.save() },
                    modifier = Modifier.fillMaxWidth().height(46.dp),
                    enabled = state.isValid,
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(text = "Salvar", fontSize = 15.sp, fontWeight = FontWeight.Medium)
                }
            }
        }
    }
}
