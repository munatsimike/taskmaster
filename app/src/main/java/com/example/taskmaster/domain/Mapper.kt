package com.example.taskmaster.domain

interface Mapper<DomainModel, DtoModel> {
    fun toDomain(dto: DtoModel): DomainModel
    fun fromDomain(domain: DomainModel): DtoModel
}