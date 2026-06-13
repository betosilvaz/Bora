package com.example.bora.repository

import com.example.bora.dto.CreateUserDto
import com.example.bora.model.User
import java.util.UUID

object UserRepository {

    private val repo: MutableList<User> = mutableListOf()

    init {
        repo.add(User(
            id = UUID.randomUUID().toString(),
            username = "gilberto",
            email = "gilberto@gmail.com",
            passwordHash = "12345678"
        ))

        repo.add(User(
            id = UUID.randomUUID().toString(),
            username = "maria_clara",
            email = "maria.clara@exemplo.com",
            passwordHash = "hash_senha_456"
        ))

        repo.add(User(
            id = UUID.randomUUID().toString(),
            username = "pedro_dev",
            email = "pedro.dev@exemplo.com",
            passwordHash = "hash_senha_789"
        ))

        repo.add(User(
            id = UUID.randomUUID().toString(),
            username = "ana_beatriz",
            email = "ana.bia@exemplo.com",
            passwordHash = "hash_senha_101"
        ))

        repo.add(User(
            id = UUID.randomUUID().toString(),
            username = "lucas_eventos",
            email = "lucas.bora@exemplo.com",
            passwordHash = "hash_senha_202"
        ))
    }

    suspend fun all() : MutableList<User> {
        return repo
    }

    suspend fun getById(userId: String) : User? {
        for(user in repo) {
            if (user.id == userId) {
                return user
            }
        }
        return null
    }

    suspend fun getByUsername(username: String) : User? {
        for(user in repo) {
            if (user.username == username) {
                return user
            }
        }
        return null
    }

    suspend fun getByEmail(email: String) : User? {
        for(user in repo) {
            if (user.email == email) {
                return user
            }
        }
        return null
    }

    suspend fun create(dto: CreateUserDto) {
        val id: String = UUID.randomUUID().toString()
        val user = User(id, dto.username, dto.email, dto.password)
        repo.add(user)
    }

}