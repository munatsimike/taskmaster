package com.teqie.taskmaster.data.mapper

interface EntityToDomain<EntityModel, DomainModel> {

    fun EntityModel.toDomainModel(): DomainModel

    fun List<EntityModel>.toDomainList(): List<DomainModel>{
        return this.map { it.toDomainModel() }
    }
}