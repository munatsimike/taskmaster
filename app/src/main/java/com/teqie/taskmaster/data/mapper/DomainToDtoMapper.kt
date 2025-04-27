package com.teqie.taskmaster.data.mapper

interface DomainToDtoMapper<DomainModel, DtoModel> {
    fun DomainModel.toDtoModel(): DtoModel
}