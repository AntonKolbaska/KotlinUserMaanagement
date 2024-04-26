package com.kotsen.kotlinusermanager.repository

import com.kotsen.kotlinusermanager.entity.Client
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ClientRepository : JpaRepository<Client, Long>{
    fun findAllClients(): List<Client>
}