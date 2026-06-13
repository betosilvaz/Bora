package com.example.bora.model

data class User(
    val id: String,
    var username: String,
    var email: String,
    var passwordHash: String,
) {}
