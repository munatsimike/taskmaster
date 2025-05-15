package com.teqie.taskmaster.data.repository.budget

import com.teqie.taskmaster.data.local.LocalDataSource
import com.teqie.taskmaster.data.mapper.APIResponseMapper.toApiResponseMessage
import com.teqie.taskmaster.data.mapper.FileToAddFileRequestDtoMapper.toDtoModel
import com.teqie.taskmaster.data.mapper.budgetphase.invoice.InvoiceDtoToEntityMapper.toEntityList
import com.teqie.taskmaster.data.mapper.budgetphase.invoice.InvoiceEntityToDomainMapper.toDomainModel
import com.teqie.taskmaster.data.mapper.budgetphase.invoice.InvoiceFileDtoToEntity.toEntityList
import com.teqie.taskmaster.data.mapper.budgetphase.invoice.InvoiceFileEntityToDomainMapper.toDomainModel
import com.teqie.taskmaster.data.mapper.budgetphase.invoice.InvoiceFileToDtoMapper.toDtoModel
import com.teqie.taskmaster.data.mapper.budgetphase.invoice.InvoiceToDtoMapper.toDtoModel
import com.teqie.taskmaster.data.remote.RemoteDataSource
import com.teqie.taskmaster.domain.Resource
import com.teqie.taskmaster.domain.model.InvoiceFile
import com.teqie.taskmaster.domain.model.budget.InvoiceRepository
import com.teqie.taskmaster.domain.model.budget.invoices.CreateInvoiceRequest
import com.teqie.taskmaster.domain.model.budget.invoices.Invoice
import com.teqie.taskmaster.domain.model.file.FileData
import com.teqie.taskmaster.ui.model.ResponseMessage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow

class InvoiceRepositoryImpl(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
): InvoiceRepository,BaseRepository() {

    // cache invoices to local db
    override fun syncInvoicesToLocal(budgetId: String): Flow<Resource<Unit>> = flow {
        emitAll(processAndCacheApiResponse(
            call = { remoteDataSource.getInvoicesByBudgetId(budgetId) },
            toEntityMapper = { it.toEntityList() },
            saveEntities = {localDataSource.saveInvoices(it)}
        )
        )
    }

    override fun createBudgetInvoice(createInvoiceRequest: CreateInvoiceRequest): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = {
                remoteDataSource.createBudgetInvoice(createInvoiceRequest.toDtoModel())
            },
            onSuccess = { response ->
                response.toApiResponseMessage()
            }
        )

    override fun deleteInvoice(invoiceId: String): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.deleteInvoice(invoiceId) },
            onSuccess = { response ->
                response
            }
        )

    override suspend fun updateInvoice(invoiceUpdateRequest: CreateInvoiceRequest): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = {

                remoteDataSource.updateInvoice(
                    invoiceId = invoiceUpdateRequest.id,
                    invoiceUpdateRequest.toDtoModel()
                )
            },
            onSuccess = { response ->
                response.toApiResponseMessage()
            }
        )

    override fun geInvoiceFiles(invoiceId: String): Flow<Resource<List<InvoiceFile>>> =
        processApiResponse(
            call = { remoteDataSource.getInvoiceFile(invoiceId) }
        ) { invoiceFile ->
          val entity =  invoiceFile.toEntityList()
            entity.map { it.toDomainModel() }
        }

    override fun fetchInvoices(budgetId: String): Flow<Resource<List<Invoice>>> = flow{
        emitAll(fetchFromLocalDb(
            fetchEntities = { localDataSource.getInvoices(budgetId) },
            fromEntityMapper = { it.toDomainModel() }
        ))
    }

    override fun updateInvoiceFile(invoiceFile: InvoiceFile): Flow<Resource<ResponseMessage>> =
        processApiResponse(
            call = { remoteDataSource.updateInvoiceFile(invoiceFile.toDtoModel()) },
            onSuccess = { response ->
                response
            }
        )

    suspend fun saveInvoiceFile(fileData: FileData){
        remoteDataSource.addInvoiceFile(fileData.toDtoModel())
    }
}