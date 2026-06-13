package com.example.bora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bora.localStorage.LocalStorage
import com.example.bora.ui.components.BottomBar
import com.example.bora.ui.screen.add.AddScreen
import com.example.bora.ui.screen.events.EventsScreen
import com.example.bora.ui.screen.forgotPassword.ForgotPasswordScreen
import com.example.bora.ui.screen.login.LoginScreen
import com.example.bora.ui.screen.login.LoginViewModel
import com.example.bora.ui.screen.map.MapScreen
import com.example.bora.ui.screen.menu.MenuScreen
import com.example.bora.ui.screen.menu.MenuViewModel
import com.example.bora.ui.screen.search.SearchScreen
import com.example.bora.ui.screen.signup.SignupScreen
import com.example.bora.ui.screen.signup.SignupViewModel
import com.example.bora.ui.theme.BoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BoraTheme {
                Router()
            }
        }
    }
}

sealed class Route(val route: String) {
    data object Login : Route("login")
    data object Signup : Route("signup")
    data object ForgotPassword: Route("forgot-password")

    data object Map : Route("map")
    data object Search : Route("search")
    data object Add : Route("add")
    data object Event : Route("event")
    data object Menu : Route("menu")

    data object Details : Route("details/{id}")
    data object Settings : Route("settings")
    data object Account : Route("account")
}

@Composable
fun Router() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    var isLoggedIn by rememberSaveable { mutableStateOf(false) }
    var startDestination by rememberSaveable { mutableStateOf("auth") }

    val bottomBarRoutes = listOf(
        Route.Map.route,
        Route.Search.route,
        Route.Add.route,
        Route.Event.route,
        Route.Menu.route,
    )

    val currentRoute = backStackEntry?.destination?.route
    val showBottomBar = currentRoute in bottomBarRoutes

    startDestination = if (isLoggedIn) "main" else "auth"

    Scaffold(bottomBar = {
        if (currentRoute in bottomBarRoutes) {
            BottomBar(
                currentTab = currentRoute ?: "Rota desconhecida",
                onClickMap = { navController.navigate(Route.Map.route) },
                onClickSearch = { navController.navigate(Route.Search.route) },
                onClickAdd = { navController.navigate(Route.Add.route) },
                onClickEvent = { navController.navigate(Route.Event.route) },
                onClickMenu = { navController.navigate(Route.Menu.route) }
            )
        }
    }) { paddingValues ->
        NavHost(navController = navController, startDestination = startDestination) {
            navigation(route = "auth", startDestination = Route.Login.route) {
                composable(Route.Login.route) {
                    val viewModel: LoginViewModel = viewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()

                    LaunchedEffect(state.isSuccess) {
                        if (state.isSuccess) {
                            navController.navigate(Route.Map.route) {
                                popUpTo(Route.Login.route) { inclusive = true }
                            }
                        }
                    }

                    LoginScreen(
                        state = state,
                        onClickLogin = { viewModel.login() },
                        onClickSignup = { navController.navigate(Route.Signup.route) },
                        onClickForgotPassword = { navController.navigate(Route.ForgotPassword.route) },
                    )
                }
                composable(Route.Signup.route) {
                    val viewModel: SignupViewModel = viewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()

                    LaunchedEffect(state.isSuccess) {
                        if (state.isSuccess) {
                            navController.navigate(Route.Map.route) {
                                popUpTo(Route.Signup.route) { inclusive = true }
                            }
                        }
                    }

                    SignupScreen(
                        state = state,
                        onClickLogin = { navController.navigate(Route.Login.route) },
                        onSignup = { viewModel.signup() },
                    )
                }
                composable(Route.ForgotPassword.route) {
                    ForgotPasswordScreen(
                        onClickBack = { navController.navigate(Route.Login.route) },
                        onSubmit = {  }
                    )
                }
            }
            navigation(route = "main", startDestination = Route.Map.route) {
                composable(Route.Map.route) {
                    MapScreen()
                }
                composable(Route.Search.route) {
                    SearchScreen()
                }
                composable(Route.Add.route) {
                    AddScreen()
                }
                composable(Route.Event.route) {
                    EventsScreen()
                }
                composable(Route.Menu.route) {
                    val viewModel: MenuViewModel = viewModel()
                    val state by viewModel.uiState.collectAsStateWithLifecycle()

                    viewModel.updateUsername(LocalStorage.getItem("username") ?: "Usuário Desconhecido")

                    MenuScreen(
                        state = state,
                        onLogout = {
                            viewModel.logout()
                            navController.navigate(Route.Login.route) {
                               popUpTo(Route.Menu.route) { inclusive = true }
                            }
                        },
                    )
                }
            }
        }
    }
}