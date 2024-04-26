package com.kotsen.kotlinusermanager.service

import com.kotsen.kotlinusermanager.dto.request.ClientRequestDTO
import com.kotsen.kotlinusermanager.dto.response.ClientResponseDTO
import com.kotsen.kotlinusermanager.entity.Client
import com.kotsen.kotlinusermanager.repository.ClientRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrNull

@Service
class ClientServiceImpl(var clientRepository: ClientRepository) {
    fun createClient(newClient: ClientRequestDTO): ClientResponseDTO {
        val save = clientRepository.save(
                Client(id = null,
                        firstName = newClient.fistName,
                        lastName = newClient.lastName,
                        email = newClient.email))
        return ClientResponseDTO(id = save.id!!,
                firstName = save.firstName,
                lastName = save.lastName,
                email = save.email)
    }

    fun updateClient(newClientId: Long, newClient: ClientRequestDTO): ClientResponseDTO {
        return clientRepository.findById(newClientId)
                .map {
                    val save = clientRepository.save(Client(id = it.id,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            email = it.email))
                    ClientResponseDTO(id = save.id!!,
                            firstName = save.firstName,
                            lastName = save.lastName,
                            email = save.email)
                }.orElseGet(null)
    }

    fun getClient(id: Long): ClientResponseDTO? {
        return clientRepository.findById(id)
                .map {
                    ClientResponseDTO(id = it.id!!,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            email = it.email)
                }.getOrNull()
    }

    fun getAllClients(): List<ClientResponseDTO> {
        val clients = clientRepository.findAllClients()
        return clients.map {
            ClientResponseDTO(
                    id = it.id!!,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    email = it.email
            )
        }
    }

    fun deleteClient(id: Long) {
        clientRepository.deleteById(id)
    }
}