package com.example.taskmaster.domain

import com.example.taskmaster.domain.model.User

/**This interface serves as the blueprint for the data repository layer, providing an abstraction
 * for data operations such as login. It decouples the data management logic from the rest of the
 * application
 * */
interface DataRepository {
    /** @param loginRequest The login request containing the user's credentials (e.g., username and password).
     * @return Result<User> A Result object that encapsulates either the successful User object or a failure with an exception.
     * */
    suspend fun login(loginRequest: LoginRequest): Result<User>
}