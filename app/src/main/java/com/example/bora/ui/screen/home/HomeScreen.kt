package com.example.bora.ui.screen.home

import android.annotation.SuppressLint
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.bora.ui.components.BottomBar
import com.example.bora.ui.screen.add.AddScreen
import com.example.bora.ui.screen.events.EventsScreen
import com.example.bora.ui.screen.map.MapScreen
import com.example.bora.ui.screen.menu.MenuScreen
import com.example.bora.ui.screen.search.SearchScreen

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(onLogout: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: "map"

    val navigateToTab: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    val pagesWithNavBar = listOf("map", "search", "add", "events", "menu")

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(bottomBar = {
            if (pagesWithNavBar.contains(currentRoute)) {
                BottomBar(
                    currentTab = currentRoute,
                    onClickMap = { navigateToTab("map") },
                    onClickSearch = { navigateToTab("search") },
                    onClickAdd = { navigateToTab("add") },
                    onClickEvent = { navigateToTab("events") },
                    onClickMenu = { navigateToTab("menu") }
                )
            }
        }) {
            NavHost(navController = navController, startDestination = "map") {
                composable("map", exitTransition = { ExitTransition.None }) {
                    MapScreen()
                }

                composable("search") {
                    SearchScreen()
                }

                composable("add") {
                    AddScreen()
                }

                composable("events") {
                    EventsScreen()
                }

                composable("menu") {
                    MenuScreen(onLogout = onLogout)
                }
            }
        }
    }
}