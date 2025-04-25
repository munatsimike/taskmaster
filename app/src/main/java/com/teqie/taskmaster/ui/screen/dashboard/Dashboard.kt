package com.teqie.taskmaster.ui.screen.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.teqie.taskmaster.data.remote.api.Resource
import com.teqie.taskmaster.domain.LoggedInUser
import com.teqie.taskmaster.domain.model.DashboardData
import com.teqie.taskmaster.domain.model.project.Project
import com.teqie.taskmaster.navigation.AppScreen
import com.teqie.taskmaster.ui.screen.DisplayScreenHeader
import com.teqie.taskmaster.ui.theme.lightGray
import com.teqie.taskmaster.ui.viewModel.DashBoardViewModel
import com.teqie.taskmaster.ui.viewModel.SharedUserViewModel
import com.teqie.taskmaster.ui.viewModel.SharedViewModel
import com.teqie.taskmaster.util.ProcessDashboardState
import com.teqie.taskmaster.util.headerData

object Dashboard {
    @Composable
    fun MainScreen(
        navController: NavController,
        sharedViewModel: SharedViewModel,
        sharedUserViewModel: SharedUserViewModel,
        dashBoardViewModel: DashBoardViewModel = hiltViewModel()
    ) {
        val project by sharedViewModel.project.collectAsState()
        val loggedInUser by sharedUserViewModel.loggedInUser.collectAsState()

        LaunchedEffect(project.id) {
            dashBoardViewModel.updateDashboardData(project.id)
        }

        val result by dashBoardViewModel.fetchDashBoardData(project.id)
            .collectAsState(initial = Resource.Loading)

        DisplayDashboard(
            result, navController, project, loggedInUser
        )
    }

    @Composable
    private fun DisplayDashboard(
        items: Resource<DashboardData>,
        navController: NavController,
        project: Project,
        loggedInUser: LoggedInUser,
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            DisplayScreenHeader(
                headerData(
                    loggedInUser = loggedInUser,
                    projectName = project.name,
                    currentPage = AppScreen.Dashboard.title,
                    showBackBtn = true
                )
            ) { navController.popBackStack() }
                ProcessDashboardState(items, navController)
        }
    }

    @Composable
    private fun ProcessDashboardState(
        networkState: Resource<DashboardData>,
        navController: NavController,
    ) {
        ProcessDashboardState(state = networkState) { dashBoardData: DashboardData ->
            DashBoardContent(
                dashboardData = dashBoardData,
                navController = navController,
            )
        }
    }

    @Composable
    private fun DashBoardContent(
        dashboardData: DashboardData,
        navController: NavController,
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .background(color = lightGray),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row {
                ScheduleCard(dashboardData.schedules) {
                    navController.navigate(
                        AppScreen.Schedule.route
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                val totalSpent = dashboardData.totals.totalPaid.toFloat()
                val totalAmount = dashboardData.totals.totalAmount.toFloat()
                 BudgetCard(
                    totalSpent, totalAmount
                ) { navController.navigate(AppScreen.Budget.route) }

                DonutChart(
                    dashboardData.totals, dashboardData.totals.totalOrfis.toString()
                ) {
                    navController.navigate(
                        AppScreen.ORFI.route
                    )
                }
            }
        }
    }
}


