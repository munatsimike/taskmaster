package com.example.taskmaster.data.mapper

interface DtoToEntityMapper <DtoModel, EntityModel>{
   fun DtoModel.toEntity(): EntityModel

    fun List<DtoModel>.toEntityList(): List<EntityModel>{
        return this.map { it.toEntity() }
    }
}