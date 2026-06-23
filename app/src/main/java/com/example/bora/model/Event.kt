package com.example.bora.model

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val EVENT_DATE_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy - HH:mm")

data class Event(
    val id: String,
    var hostId: String,
    var name: String,
    var description: String,
    var address: String,
    var date: LocalDateTime,
    var hasGuestLimit: Boolean,
    var guestLimit: Int,
    var isPublic: Boolean,
    val extraSection: MutableList<ExtraSection> = mutableListOf(),
    val participants: MutableList<User> = mutableListOf(),
) {}

data class ExtraSection(
    var title: String,
    var body: String,
) {}
