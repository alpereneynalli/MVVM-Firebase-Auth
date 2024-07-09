package net.firebase_auth.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import net.firebase_auth.ui.auth.AuthState
import net.firebase_auth.ui.auth.AuthViewModel
import net.firebase_auth.ui.auth.LoginScreen
import net.firebase_auth.ui.auth.SignupScreen
import net.firebase_auth.ui.home.HomeScreen


@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = if (viewModel.authState.value is AuthState.Authenticated) ROUTE_HOME else ROUTE_LOGIN
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(viewModel, navToHomePage = {
                navController.navigate(ROUTE_HOME) {
                    popUpTo(ROUTE_SIGNUP) { inclusive = true }
                }
            }, navToSignUpPage = {
                navController.navigate(ROUTE_SIGNUP) {
                    popUpTo(ROUTE_LOGIN) { inclusive = true }
                }
            })
        }
        composable(ROUTE_SIGNUP) {
            SignupScreen(viewModel,
                navToHomePage = {
                    navController.navigate(ROUTE_HOME) {
                        popUpTo(ROUTE_SIGNUP) { inclusive = true }
                    }
                },
                navToLoginPage = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_SIGNUP) { inclusive = true }
                    }
                })
        }
        composable(ROUTE_HOME) {
            HomeScreen(viewModel,
                navToLoginPage = {
                    navController.navigate(ROUTE_LOGIN) {
                        popUpTo(ROUTE_HOME) { inclusive = true }
                    }
                })
        }
    }
}