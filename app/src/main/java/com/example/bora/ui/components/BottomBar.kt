package com.example.bora.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.Event
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    currentTab: String,
    onClickMap: () -> Unit,
    onClickSearch: () -> Unit,
    onClickAdd: () -> Unit,
    onClickEvent: () -> Unit,
    onClickMenu: () -> Unit
) {
    val iconModifier = Modifier.size(32.dp)
    val buttonModifier = Modifier.size(56.dp)

    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column {
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .navigationBarsPadding()
                ) {
                    IconButton(
                        modifier = buttonModifier,
                        onClick = onClickMap,
                        colors = if(currentTab == "map") IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary) else IconButtonDefaults.iconButtonColors()
                    ) {
                        Icon(imageVector = Icons.Outlined.Map, contentDescription = null, modifier = iconModifier)
                    }
                    IconButton(
                        modifier = buttonModifier,
                        onClick = onClickSearch,
                        colors = if(currentTab == "search") IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary) else IconButtonDefaults.iconButtonColors()
                    ) {
                        Icon(imageVector = Icons.Outlined.Search, contentDescription = null, modifier = iconModifier)
                    }

                    // Espaço vazio para o botão central "respirar"
                    Box(modifier = Modifier.size(56.dp))

                    IconButton(
                        modifier = buttonModifier,
                        onClick = onClickEvent,
                        colors = if(currentTab == "event") IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary) else IconButtonDefaults.iconButtonColors()
                    ) {
                        Icon(imageVector = Icons.Outlined.Event, contentDescription = null, modifier = iconModifier)
                    }
                    IconButton(
                        modifier = buttonModifier,
                        onClick = onClickMenu,
                        colors = if(currentTab == "menu") IconButtonDefaults.iconButtonColors(contentColor = MaterialTheme.colorScheme.primary) else IconButtonDefaults.iconButtonColors()
                    ) {
                        Icon(imageVector = Icons.Outlined.Menu, contentDescription = null, modifier = iconModifier)
                    }
                }
            }
        }

        IconButton(
            onClick = onClickAdd,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .size(72.dp),
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            )
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}