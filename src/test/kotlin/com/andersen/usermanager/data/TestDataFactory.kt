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

}