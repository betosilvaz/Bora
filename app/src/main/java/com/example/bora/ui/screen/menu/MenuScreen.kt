package com.example.bora.ui.screen.menu

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MenuScreen(onLogout: () -> Unit) {
    Surface(color = MaterialTheme.colorScheme.primary) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.statusBarsPadding()
        ) {
            Surface(
                color = Color.Transparent,
                shape = CircleShape,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .border(2.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
            ) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = null,
                    modifier = Modifier
                        .size(128.dp)
                        .padding(8.dp)
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "John Doe", fontSize = 28.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(16.dp))
            Surface(shape = RoundedCornerShape(24.dp)) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                ) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "Menu", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {  }) {
                        Text("Conta")
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = {  }) {
                        Text("Configurações")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(modifier = Modifier.fillMaxWidth(), onClick = onLogout) {
                        Text("Sair")
                    }
                }
            }
        }
    }
}