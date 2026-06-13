package com.example.bora.service

import com.example.bora.Response
import com.example.bora.dto.CreateUserDto
import com.example.bora.model.User
import com.example.bora.repository.UserRepository

object AuthenticationService {

    private suspend fun authenticate(email: String, password: String) : Boolean {
        val user: User? = UserRepository.getByEmail(email)
        if (user == null)  return false
        if (user.passwordHash != password) return false
        return true
    }

    suspend fun login(email: String, password: String) : Boolean {
        val isValidCredentials = authenticate(email, password)
        if (isValidCredentials) return true
        return false
    }

    suspend fun signup(dto: CreateUserDto) : Response {
        val isAnyBlank = dto.username.isBlank() || dto.email.isBlank() || dto.password.isBlank() || dto.confirmPassword.isBlank()
        if (isAnyBlank) {
            return Response("ERROR", "Todos os campos devem ser preenchidos.")
        }

        if (!dto.email.contains('@')) {
            return Response("ERROR", "Insíra um e-mail válido.")
        }

        if (dto.password != dto.confirmPassword) {
            return Response("ERROR", "As senhas não coincidem.")
        }

        if (dto.password.length < 8) {
            return Response("ERROR", "A senha deve ter no mínimo 8 caracteres.")
        }

        if (UserRepository.getByUsername(dto.username) != null) {
            return Response("ERROR", "Esse usuário já está em uso.")
        }

        if (UserRepository.getByEmail(dto.email) != null) {
            return Response("ERROR", "E-mail já está em uso.")
        }

        return Response("SUCCESS", "Usuário cadastrado com sucesso.")
    }

}