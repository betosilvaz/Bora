package com.example.bora.repository

import com.example.bora.model.Event
import java.util.UUID

object EventRepository {

    private val repo: MutableList<Event> = mutableListOf()

    init {
        repo.addAll(listOf(
            Event(
                id = UUID.randomUUID().toString(),
                hostId = "user-1",
                name = "Churrasco na Praia",
                description = "Churrasco open bar com música ao vivo e piscina natural. Traga sua canga e venha curtir!",
                address = "Av. Boa Viagem, 1500 - Boa Viagem, Recife - PE",
                date = "14/06/26 - 12:00",
                hasGuestLimit = true,
                guestLimit = 30,
                isPublic = true
            ),
            Event(
                id = UUID.randomUUID().toString(),
                hostId = "user-1",
                name = "Rapel no Marco Zero",
                description = "Venha praticar rapel nas paredes do prédio histórico com instrutor certificado. Equipamento incluso.",
                address = "Praça do Marco Zero, s/n - Recife Antigo, Recife - PE",
                date = "20/06/26 - 19:30",
                hasGuestLimit = true,
                guestLimit = 10,
                isPublic = true
            ),
            Event(
                id = UUID.randomUUID().toString(),
                hostId = "user-2",
                name = "Feira de Artesanato",
                description = "Exposição e venda de artesanato local com artesãos de Pernambuco. Música regional ao vivo.",
                address = "Rua do Bom Jesus, 200 - Recife Antigo, Recife - PE",
                date = "18/06/26 - 15:00",
                hasGuestLimit = false,
                guestLimit = 0,
                isPublic = true
            ),
            Event(
                id = UUID.randomUUID().toString(),
                hostId = "user-2",
                name = "Piquenique no Parque",
                description = "Piquenique colaborativo no Parque da Jaqueira. Cada um leva um prato e compartilhamos!",
                address = "Parque da Jaqueira, s/n - Jaqueira, Recife - PE",
                date = "25/06/26 - 09:00",
                hasGuestLimit = false,
                guestLimit = 0,
                isPublic = true
            ),
            Event(
                id = UUID.randomUUID().toString(),
                hostId = "user-1",
                name = "Sarau na Aurora",
                description = "Noite de poesia, música e microfone aberto com artistas independentes. Cerveja artesanal cortesia.",
                address = "Rua da Aurora, 500 - Santo Amaro, Recife - PE",
                date = "22/06/26 - 20:00",
                hasGuestLimit = true,
                guestLimit = 50,
                isPublic = true
            )
        ))
    }

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