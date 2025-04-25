package com.teqie.taskmaster.data.mapper

import com.teqie.taskmaster.data.local.db.enties.LoggedInUserEntity
import com.teqie.taskmaster.data.remote.dto.auth.LoginRequestDto
import com.teqie.taskmaster.data.remote.dto.user.UserApiResponseDto
import com.teqie.taskmaster.domain.LoggedInUser
import com.teqie.taskmaster.domain.LoginRequest
import com.teqie.taskmaster.domain.model.auth.User

object AuthMapper {
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

    fun LoginRequest.toDto(): LoginRequestDto {
        return LoginRequestDto(username = username, password = password)
    }
}
