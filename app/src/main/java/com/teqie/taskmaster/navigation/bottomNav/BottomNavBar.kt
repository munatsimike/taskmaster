package com.teqie.taskflow.navigation.bottomNav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.teqie.taskflow.navigation.bottomNav.NavBarItems.moreItems
import com.teqie.taskflow.navigation.bottomNav.NavBarItems.primaryItems
import com.teqie.taskmaster.navigation.bottomNav.routesToHideBottomNav

@Composable
fun BottomNavBar(navHostController: NavHostController) {
    var moreMenuExpanded by remember { mutableStateOf(false) }
    var isBarVisible by rememberSaveable { mutableStateOf(false) }

    val backStackEntry by navHostController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    LaunchedEffect(currentRoute) {
        isBarVisible = isBottomNavVisible(currentRoute)
    }

    AnimatedVisibility(
        visible = isBarVisible,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar {
            primaryItems().forEach { navItem ->
                NavigationBarItem(
                    selected = navItem.route == currentRoute,
                    onClick = {
                        navHostController.navigate(navItem.route) {
                            popUpTo(navHostController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true

                        }
                    },
                    icon = { Icon(imageVector = navItem.image, contentDescription = "") },
                    label = { Text(text = navItem.title) }
                )
            }

            NavigationBarItem(
                selected = false,
                onClick = { moreMenuExpanded = true },
                icon = {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "more icon"
                    )
                },
                label = { Text(text = "More") }
            )
        }

        MoreItemsMenu(moreMenuExpanded, navHostController) { moreMenuExpanded = false }
    }
}

@Composable
private fun MoreItemsMenu(
    isExpanded: Boolean,
    navHostController: NavHostController,
    onDismiss: () -> Unit,
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp
    // Adjust the offset to move the dropdown to the right
    DropdownMenu(
        expanded = isExpanded,
        onDismissRequest = onDismiss,
        offset = DpOffset(x = screenWidth.dp - 120.dp, y = 0.dp), // Adjust x to move right
        properties = PopupProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
        )
    ) {
        moreItems().forEach { item ->
            DropdownMenuItem(
                text = { Text(text = item.title) },
                onClick = {
                    navHostController.navigate(item.route) {
                        popUpTo(navHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    onDismiss() // Close the menu after selection
                },
                leadingIcon = { Icon(imageVector = item.image, contentDescription = item.title) }
            )
        }
    }
}

fun isBottomNavVisible(currentRoute: String?): Boolean {
    return currentRoute?.let { it !in routesToHideBottomNav } ?: false
}
