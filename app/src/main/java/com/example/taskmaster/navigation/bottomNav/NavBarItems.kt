package com.example.taskflow.nagivation.bottomNav

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.taskmaster.R
import com.example.taskmaster.navigation.AppScreen
import com.example.taskmaster.navigation.bottomNav.BarItem

object NavBarItems {
    @Composable
    fun primaryItems() = listOf(
        BarItem(
            title = AppScreen.Projects.title,
            image = ImageVector.vectorResource(id = R.drawable.list_24px),
            route = AppScreen.Projects.route
        ),

        BarItem(
            title = AppScreen.Dashboard.title,
            image = ImageVector.vectorResource(id = R.drawable.dashboard_24px),
            route = AppScreen.Dashboard.route
        ),

        BarItem(
            title = AppScreen.Schedule.title,
            image = ImageVector.vectorResource(id = R.drawable.date_range_24px),
            route = AppScreen.Schedule.route
        ),
    )

    @Composable
    fun moreItems() = listOf(

        BarItem(
            title = AppScreen.Budget.title,
            image = ImageVector.vectorResource(id = R.drawable.money_bag_24px),
            route = AppScreen.Budget.route
        ),

        BarItem(
            title = AppScreen.ORFI.title,
            image = ImageVector.vectorResource(id = R.drawable.help_center_24px),
            route = AppScreen.ORFI.route
        ),

        BarItem(
            title = AppScreen.Teams.title,
            image = ImageVector.vectorResource(id = R.drawable.group_24px),
            route = AppScreen.Teams.route
        ),

        BarItem(
            title = AppScreen.Folders.title,
            image = ImageVector.vectorResource(id = R.drawable.folder_open_24px),
            AppScreen.Folders.route
        )
    )
}