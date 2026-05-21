package com.example.bora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bora.ui.screen.home.HomeScreen
import com.example.bora.ui.screen.login.LoginScreen
import com.example.bora.ui.screen.login.LoginService
import com.example.bora.ui.screen.login.LoginViewModel
import com.example.bora.ui.screen.login.ResponseStatus
import com.example.bora.ui.screen.signup.SignupScreen
import com.example.bora.ui.theme.BoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BoraTheme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = viewModel()

            LoginScreen(
                onLogin = { username, password ->
                    val response = LoginService.login(username, password)
                    if (response.status == ResponseStatus.SUCCESS) {
                        navController.navigate(Screen.Home.route)
                    } else {
                        viewModel.setErrorMessage(response.message)
                    }
                },
                onClickSignup = {
                    navController.navigate(Screen.Signup.route)
                }
            )
        }

        composable(Screen.Signup.route) {
            SignupScreen(
                onSignup = {
                  // TODO: requisição de cadastro, login e redirecionamento pra pagina Home
                },
                onClickLogin = {
                    navController.navigate(Screen.Login.route)
                }
            )
        }

        composable(Screen.Home.route) {
            HomeScreen()
        }

    }
}