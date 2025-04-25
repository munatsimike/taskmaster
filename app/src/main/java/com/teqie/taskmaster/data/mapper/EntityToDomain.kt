package com.teqie.taskmaster.data.mapper

interface EntityToDomain<EntityModel, DomainModel> {

    fun EntityModel.toDomainModel(): DomainModel

    fun toDomainList(entityList: List<EntityModel>): List<DomainModel>{
        return entityList.map { it.toDomainModel() }
    }
}