package com.example.taskmaster.data.mapper

import com.example.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.example.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.example.taskmaster.domain.LoggedInUser
import com.example.taskmaster.domain.model.User

object UserMapper {
    fun UserApiResponseDto.toDomainUser(): User {
        return User(
            avatar = this.userDetails.avatar,
            name = this.name,
            isSuperUser = this.isSuperUser,
        )
    }

    fun LoggedInUserEntity.toLoggedInUser(): LoggedInUser {
        return LoggedInUser(
            id = id,
            name = name,
            email = email,
            phone = phone,
            isSuperUser = isSuperUser == 1,
            avatar = avatar
        )
    }

    fun UserApiResponseDto.toLoggedInUserEntity(): LoggedInUserEntity {
        return LoggedInUserEntity(
            id = this.id,
            name = this.name,
            email = this.email,
            phone = this.phone,
            isSuperUser = this.isSuperUser,
            avatar = this.userDetails.avatar
        )
    }
}
