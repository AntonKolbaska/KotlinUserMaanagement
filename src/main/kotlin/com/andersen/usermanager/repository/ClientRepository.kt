package com.andersen.usermanager.repository

import com.andersen.usermanager.entity.Client
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long> {
//    override fun findAll(pageable: Pageable): Page<Client>
    fun findByEmail(email: String): Client?

    @Query(
        "SELECT c FROM clients c " +
                "WHERE c.firstName LIKE CONCAT(:firstName, '%') AND c.lastName LIKE CONCAT(:lastName, '%')"
    )
    fun findByFirstNameAndLastName(
        @Param("firstName") firstName: String,
        @Param("lastName") lastName: String,
        pageable: Pageable
    ): Page<Client>
}