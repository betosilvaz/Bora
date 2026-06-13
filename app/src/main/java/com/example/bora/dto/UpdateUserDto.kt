package com.example.bora.dto

data class UpdateUserDto (
    val id: String,
    val username: String,
    val email: String,
    val currentPassword: String,
    val newPassword: String,
    val confirmNewPassword: String,
)