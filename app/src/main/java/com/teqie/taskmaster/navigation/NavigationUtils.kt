package com.teqie.taskmaster.navigation

import androidx.navigation.NavController

fun navigateBasedOnToken(hasToken: Boolean, navController: NavController) {
    navController.navigate(
        if (hasToken) AppScreen.Projects.route else AppScreen.Login.route
    ) {
        popUpTo(AppScreen.SplashScreen.route) { inclusive = true }
    }
}