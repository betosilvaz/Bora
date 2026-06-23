package com.example.bora.dto

import com.example.bora.model.ExtraSection
import java.time.LocalDateTime

data class CreateEventDto(
    val hostId: String,
    val name: String,
    val description: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val date: LocalDateTime,
    val hasGuestLimit: Boolean,
    val guestLimit: Int,
    val isPublic: Boolean,
    val extraSection: MutableList<ExtraSection> = mutableListOf(),
) {}