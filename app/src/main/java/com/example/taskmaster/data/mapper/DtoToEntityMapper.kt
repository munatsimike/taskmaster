package com.example.taskmaster.data.mapper

interface DtoToEntityMapper <DtoModel, EntityModel>{
   fun DtoModel.toEntity(): EntityModel

    fun toEntityList(dtoList: List<DtoModel>): List<EntityModel>{
        return dtoList.map { it.toEntity() }
    }
}