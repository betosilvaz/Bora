package com.example.bora.service

import com.example.bora.Response
import com.example.bora.dto.UpdateUserDto
import com.example.bora.model.User
import com.example.bora.repository.UserRepository

object UserService {

    suspend fun update(dto: UpdateUserDto) : Response {
        val user: User? = UserRepository.getById(dto.id)

        if (user == null) return Response("ERROR", "Erro ao atualizar dados: Usuário não encontrado.")

        if (dto.confirmNewPassword.isNotBlank() || dto.newPassword.isNotBlank()) {
            if (user.passwordHash != dto.currentPassword) {
                return Response("ERROR", "Senha atual incorreta.")
            }

            if (dto.newPassword != dto.confirmNewPassword) {
                return Response("ERROR", "Os campos de nova senha devem coincidir.")
            }

            if (dto.newPassword.length < 8) {
                return Response("ERROR", "A nova senha deve ter no mínimo 8 caracteres")
            }

            user.passwordHash = dto.newPassword
        }

        user.username = dto.username
        user.email = dto.email
        return Response("SUCCESS", "Atualizado com sucesso.")
    }

}