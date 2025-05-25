package com.teqie.taskmaster.ui.viewModel.orfi

import ORFIFile
import androidx.lifecycle.viewModelScope
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.useCases.orfi.DeleteOrfiUseCase
import com.teqie.taskmaster.domain.useCases.orfi.GetOrfiFilesUseCase
import com.teqie.taskmaster.domain.useCases.orfi.GetOrfiUseCase
import com.teqie.taskmaster.domain.useCases.orfi.SyncOrfiToLocalDbUseCase
import com.teqie.taskmaster.ui.model.orfi.Orfi
import com.teqie.taskmaster.ui.viewModel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ORFIViewModel @Inject constructor(
    private val getORFIUseCase: GetOrfiUseCase,
    private val getORFIFilesUseCase: GetOrfiFilesUseCase,
    private val deleteORFIUsecase: DeleteOrfiUseCase,
    private val syncOrfiToLocalDbUseCase: SyncOrfiToLocalDbUseCase
) : BaseViewModel() {
    private val _orfiState = MutableStateFlow<Resource<List<Orfi>>>(Resource.Loading)
    val orfiState: StateFlow<Resource<List<Orfi>>> = _orfiState

    private val _orfiFiles = MutableStateFlow<Resource<List<ORFIFile>>>(Resource.Loading)
    val orfiFiles: StateFlow<Resource<List<ORFIFile>>> = _orfiFiles

    fun getORFIFiles(orfiId: String){
        viewModelScope.launch {
            delay(500)
            getORFIFilesUseCase(orfiId).collect{
                _orfiFiles.value = it
            }
        }
    }

    fun getORFI(projectId: String) {
        viewModelScope.launch {
            getORFIUseCase(projectId).collect{
                _orfiState.value = it
            }
        }
    }

    fun showConfirmDeleteDialog(selectedOrfi: Orfi) {
        selectedItem(selectedOrfi.question)
        selectedItemId(selectedOrfi.id)
        showConfirmDeleteDialog()
    }

    fun onDeleteOrfi(orfiId: String) {
        viewModelScope.launch {
            deleteORFIUsecase(orfiId).collect {
               handleResponse(it)
            }
            hideConfirmDeleteDialog()
        }
    }

    fun syncOrfiToLocalDb(projectId: String){
        syncOrfiToLocalDbUseCase(projectId)
    }
}