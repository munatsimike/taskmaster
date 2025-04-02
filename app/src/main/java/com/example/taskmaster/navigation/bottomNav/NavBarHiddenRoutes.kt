package com.example.taskmaster.navigation.bottomNav

import com.example.taskmaster.navigation.AppScreen

// Define a set of routes that should hide the bottom navigation
val routesToHideBottomNav = setOf(
    AppScreen.Projects.route,
    AppScreen.Login.route,
)