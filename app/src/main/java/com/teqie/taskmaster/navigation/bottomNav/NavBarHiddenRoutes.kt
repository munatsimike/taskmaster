package com.teqie.taskmaster.navigation.bottomNav

import com.teqie.taskmaster.navigation.AppScreen

// Define a set of routes that should hide the bottom navigation
val routesToHideBottomNav = setOf(
    AppScreen.Projects.route,
    AppScreen.Login.route,
    AppScreen.SplashScreen.route
)