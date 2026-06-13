package com.example.bora.repository

import com.example.bora.model.User

object UserRepository {

    private val repo: MutableList<User> = mutableListOf()

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

}