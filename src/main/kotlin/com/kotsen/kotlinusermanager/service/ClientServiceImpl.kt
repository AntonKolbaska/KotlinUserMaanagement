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
                        email = newClient.email,
                        job = newClient.job,
                        position = newClient.position))
        return ClientResponseDTO(id = save.id!!,
                firstName = save.firstName,
                lastName = save.lastName,
                email = save.email,
                job = save.job,
                position = save.position)
    }

    fun updateClient(newClientId: Long, newClient: ClientRequestDTO): ClientResponseDTO {
        return clientRepository.findById(newClientId)
                .map {
                    val save = clientRepository.save(Client(id = it.id,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            email = it.email,
                            job = it.job,
                            position = it.position))
                    ClientResponseDTO(id = save.id!!,
                            firstName = save.firstName,
                            lastName = save.lastName,
                            email = save.email,
                            job = save.job,
                            position = save.position)
                }.orElseGet(null)
    }

    fun getClient(id: Long): ClientResponseDTO? {
        return clientRepository.findById(id)
                .map {
                    ClientResponseDTO(id = it.id!!,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            email = it.email,
                            job = it.job,
                            position = it.position)
                }.getOrNull()
    }

    fun getAllClients(): List<ClientResponseDTO> {
        val clients = clientRepository.findAllClients()
        return clients.map {
            ClientResponseDTO(
                    id = it.id!!,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    email = it.email,
                    job = it.job,
                    position = it.position
            )
        }
    }

    fun deleteClient(id: Long) {
        clientRepository.deleteById(id)
    }
}