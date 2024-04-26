package com.kotsen.kotlinusermanager.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity(name = "client")
data class Client(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long?,
        var firstName: String,
        var lastName: String,
        var email: String,
        var job: String?,
        var position: String?)