package com.andersen.usermanager.repository

import com.andersen.usermanager.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long> {
    override fun findAll(): List<Client>
}