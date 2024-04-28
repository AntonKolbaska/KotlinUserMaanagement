package com.andersen.usermanager.repository

import com.andersen.usermanager.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long> {
    override fun findAll(): List<Client>
    fun findByEmail(email: String): Client?

    @Query(
        "SELECT c FROM clients c " +
                "WHERE c.firstName LIKE CONCAT(:firstName, '%') AND c.lastName LIKE CONCAT(:lastName, '%')"
    )
    fun findByFirstNameAndLastName(
        @Param("firstName") firstName: String,
        @Param("lastName") lastName: String
    ): List<Client>
}