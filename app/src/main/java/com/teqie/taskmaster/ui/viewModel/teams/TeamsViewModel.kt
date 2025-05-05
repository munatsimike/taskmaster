package com.teqie.taskmaster.ui.viewModel.teams

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.domain.useCases.user.GetTeamsByProjectUseCase
import com.teqie.taskmaster.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val getTeamsByProjectUseCase: GetTeamsByProjectUseCase,
) : BaseViewModel() {

    private val _fetchProjectUsers = MutableStateFlow(false)
    val fetchProjectUsers: StateFlow<Boolean> = _fetchProjectUsers

    private val _teamsByProjectState =
        MutableStateFlow<Resource<List<TeamMember>>>(Resource.Loading)
    val teamsByProjectState: StateFlow<Resource<List<TeamMember>>> = _teamsByProjectState

    fun getTeamsByProject(projectId: String) {
        viewModelScope.launch {
            getTeamsByProjectUseCase(projectId).collect {
                _teamsByProjectState.value = it
            }
        }
    }
}