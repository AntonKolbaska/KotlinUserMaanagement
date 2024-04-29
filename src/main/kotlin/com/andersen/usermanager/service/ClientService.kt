package com.andersen.usermanager.service

import com.andersen.usermanager.dto.request.ClientRequestDTO
import com.andersen.usermanager.dto.response.ClientResponseDTO
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface ClientService {
    fun createClient(client: ClientRequestDTO): ClientResponseDTO
    fun updateClient(clientId: Long, clientDTO: ClientRequestDTO): ClientResponseDTO
    fun getClient(id: Long): ClientResponseDTO
    fun getAllClients(pageable: Pageable): Page<ClientResponseDTO>
    fun getClientsByNames(firstName: String, lastName : String, pageable: Pageable) : Page<ClientResponseDTO>
    fun findClientsByString(search: String, pageable: Pageable): Page<ClientResponseDTO>
    fun deleteClient(id: Long)
}