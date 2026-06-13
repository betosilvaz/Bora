package com.example.bora.model

data class Event(
    val id: String,
    var hostId: String,
    var name: String,
    var description: String,
    var address: String,
    var date: String,
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
