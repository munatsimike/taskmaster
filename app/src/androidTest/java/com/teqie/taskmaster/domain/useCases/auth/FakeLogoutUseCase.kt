package com.teqie.taskmaster.domain.useCases.auth


class FakeLogoutUseCase : LogoutUseCase {
    override suspend fun logout() {
        println("Fake logout called")
    }
}