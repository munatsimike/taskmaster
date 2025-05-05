package com.teqie.taskmaster.domain.mapper

interface UiToDomainModel<UiModel, DomainModel> {
    fun UiModel.toDomainModel(): DomainModel
}