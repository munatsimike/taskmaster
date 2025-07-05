package com.teqie.taskmaster.ui.viewModel.teams

import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.teamMember.TeamMember
import com.teqie.taskmaster.domain.useCases.user.GetAllTeamMembersUserCase
import com.teqie.taskmaster.domain.useCases.user.GetTeamsByProjectUseCase
import com.teqie.taskmaster.domain.useCases.user.SyncTeamsToLocalDbUseCase
import com.teqie.taskmaster.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeamsViewModel @Inject constructor(
    private val getTeamsByProjectUseCase: GetTeamsByProjectUseCase,
    private val syncTeamsToLocalDbUseCase: SyncTeamsToLocalDbUseCase,
    private val getAllTeamMembersUseCase: GetAllTeamMembersUserCase
) : BaseViewModel() {

    private val _fetchAllTeamMembers = MutableStateFlow<Resource<List<TeamMember>>>(Resource.Loading)
    val teamMembersState:  StateFlow<Resource<List<TeamMember>>> = _fetchAllTeamMembers

    private val _teamsByProjectState = MutableStateFlow<Resource<List<TeamMember>>>(Resource.Loading)
    val teamsByProjectState: StateFlow<Resource<List<TeamMember>>> = _teamsByProjectState

    fun syncTeamsToLocalDb(projectId: String){
        viewModelScope.launch {
            syncTeamsToLocalDbUseCase(projectId).collect{
            }
        }
    }

    fun getTeamsByProject(projectId: String) {
        viewModelScope.launch {
            getTeamsByProjectUseCase(projectId).collect{
                _teamsByProjectState.value = it
            }
        }
    }

    fun getAllTeamMembers(){
        viewModelScope.launch {
            getAllTeamMembersUseCase().collect{
                _fetchAllTeamMembers.value = it
            }
        }
    }
}