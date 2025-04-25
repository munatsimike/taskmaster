package com.teqie.taskmaster.domain

/**This class holds the username and password provided by the user for logging in. It also includes
* a validation function to ensure that the login credentials meet basic requirements before being
* sent to the server.
**/

data class LoginRequest(val username: String, val password: String){
    //checks if username and password is not blank
    fun isValid(): Boolean {
        return username.isNotBlank() && password.isNotBlank()
    }
}