package com.example.bora.repository

import com.example.bora.model.Event

object EventRepository {

    private val repo: MutableList<Event> = mutableListOf()

    fun all() : MutableList<Event> {
        return repo
    }

    suspend fun getById(eventId: String) : Event? {
        for(event in repo) {
            if (event.id == eventId) {
                return event
            }
        }
        return null
    }

    suspend fun getByUserId(userId: String) : MutableList<Event> {
        val events: MutableList<Event> = mutableListOf()
        for(event in repo) {
            if (event.hostId == userId) {
                events.add(event)
            }
        }
        return events
    }

    suspend fun getByName(name: String) : MutableList<Event> {
        val events: MutableList<Event> = mutableListOf()
        for(event in repo) {
            if (event.name == name) {
                events.add(event)
            }
        }
        return events
    }

}