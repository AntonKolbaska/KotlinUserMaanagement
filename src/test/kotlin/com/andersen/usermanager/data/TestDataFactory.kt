package com.andersen.usermanager.data

import com.andersen.usermanager.dto.request.ClientRequestDTO
import com.andersen.usermanager.entity.Client
import com.andersen.usermanager.entity.Gender

object TestDataFactory {

    fun createGenderDefinedClientRequestDTO(): ClientRequestDTO {
        return ClientRequestDTO(
            firstName = "John",
            lastName = "Doe",
            email = "johndoe@example.com",
            gender = Gender.MALE,
            job = "Software Engineer",
            position = "Senior Developer"
        )
    }

    fun createGenderNotDefinedClientRequestDTO(): ClientRequestDTO {
        return ClientRequestDTO(
            firstName = "John",
            lastName = "Doe",
            email = "johndoe@example.com",
            gender = null,
            job = "Software Engineer",
            position = "Senior Developer"
        )
    }

    fun createClient(): Client {
        return Client(
            id = 1L,
            firstName = "John",
            lastName = "Doe",
            email = "johndoe@example.com",
            gender = Gender.MALE,
            job = "Software Engineer",
            position = "Senior Developer"
        )
    }

    fun createClient_1(): Client {
        return Client(
            id = 2L,
            firstName = "Jason",
            lastName = "Dawson",
            email = "johndoe@example.com",
            gender = Gender.MALE,
            job = "Software Engineer",
            position = "Senior Developer"
        )
    }

    fun createListOfClients(count: Int): List<Client> {
        val clients = mutableListOf<Client>()
        for (i in 1..count) {
            val client = Client(
                id = i.toLong(),
                firstName = "ClientFirstName$i",
                lastName = "ClientLastName$i",
                email = "client$i@example.com",
                gender = if (i % 2 == 0) Gender.MALE else Gender.FEMALE,
                job = "Job$i",
                position = "Position$i"
            )
            clients.add(client)
        }
        return clients
    }
}