package com.example.bora.dto

data class CreateUserDto(
    val username: String,
    val email: String,
    val password: String,
    val confirmPassword: String,
) {}
