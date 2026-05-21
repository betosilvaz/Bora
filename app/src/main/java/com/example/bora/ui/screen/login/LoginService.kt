package com.example.bora.ui.screen.login

object LoginService {

    fun login(username: String, password: String): LoginResponse {
        val response = LoginResponse(status = ResponseStatus.ERROR, message = "E-mail já está em uso")
        return response
    }

}

data class LoginResponse(
    val status: ResponseStatus,
    val message: String
)

enum class ResponseStatus {
    SUCCESS,
    ERROR
}