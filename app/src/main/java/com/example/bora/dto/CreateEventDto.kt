package com.example.bora.dto

import com.example.bora.model.ExtraSection

data class CreateEventDto(
    val hostId: String,
    val name: String,
    val description: String,
    val address: String,
    val date: String,
    val hasGuestLimit: Boolean,
    val guestLimit: Int,
    val isPublic: Boolean,
    val extraSection: MutableList<ExtraSection> = mutableListOf(),
) {}