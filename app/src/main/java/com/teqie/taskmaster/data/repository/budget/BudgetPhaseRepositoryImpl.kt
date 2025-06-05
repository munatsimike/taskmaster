package com.teqie.taskmaster.data.repository.budget

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.mapper.APIResponseMapper.toApiResponseMessage
import com.teqie.taskmaster.data.mapper.BudgetPhaseMapper.toCreateBudgetPhaseDto
import com.teqie.taskmaster.data.mapper.BudgetPhaseMapper.toUpdateBudgetPhaseDto
import com.teqie.taskmaster.data.mapper.budgetphase.BudgetPhaseDtoToEntityMapper.toEntityList
import com.teqie.taskmaster.data.mapper.budgetphase.BudgetPhaseEntityToDomainMapper.toDomainModel
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.data.repository.BaseRepository
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.budget.BudgetPhase
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseFormData
import com.teqie.taskmaster.domain.model.budget.BudgetPhaseRepository
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class BudgetPhaseRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : BudgetPhaseRepository, BaseRepository() {

    // fetch from local db
    override fun fetchBudgetPhases(projectId: String): Flow<Resource<List<BudgetPhase>>> = flow {
        emitAll(fetchFromLocalDb(
            fetchEntities = { localDataSource.getBudgetPhases(projectId) },
            fromEntityMapper = { it.toDomainModel() }
        ))
    }

    override suspend fun updateBudgetPhase(budgetPhaseFormData: BudgetPhaseFormData): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.updateBudgetPhase(budgetPhaseFormData.toUpdateBudgetPhaseDto()) },
            onSuccess = { response ->
                ResponseMessage(response.message)
            })

    override fun createBudgetPhase(budgetPhase: BudgetPhaseFormData): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.createBudgetPhase(budgetPhase.toCreateBudgetPhaseDto()) },
            onSuccess = { response ->
                response.toApiResponseMessage()
            })

    override fun deleteBudgetPhase(budgetId: String): Flow<Resource<ResponseMessage>> =
        flow {
            val response = remoteDataSource.deleteBudgetPhase(budgetId)
            if (response.isSuccessful) {
                emit(Resource.Success(ResponseMessage("BudgetPhase phase deleted successfully")))
            }
        }

    override fun syncBudgetPhasesToLocal(projectId: String): Flow<Resource<Unit>> = flow {
        emitAll(processAndCacheApiResponse(
            call = { remoteDataSource.getBudgetPhases(projectId) },
            toEntityMapper = { it.toEntityList() },
            saveEntities = { localDataSource.saveBudgetPhase(it) },
            clearTable = {localDataSource.deleteBudgetPhases()}
        )
        )
    }
}