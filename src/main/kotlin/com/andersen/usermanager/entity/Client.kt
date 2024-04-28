package com.andersen.usermanager.entity

import jakarta.persistence.*

@Entity(name = "clients")
data class Client(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,
    var firstName: String,
    var lastName: String,
    var email: String,
    @Enumerated(EnumType.STRING)
    var gender: Gender,
    var job: String?,
    var position: String?
)