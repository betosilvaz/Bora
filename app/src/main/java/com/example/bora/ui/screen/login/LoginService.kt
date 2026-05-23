package com.example.bora.ui.screen.login

import kotlinx.coroutines.delay

object LoginService {

    suspend fun login(username: String, password: String): LoginResponse {
        delay(0)
        val response = LoginResponse(status = ResponseStatus.SUCCESS, message = "E-mail já está em uso")
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