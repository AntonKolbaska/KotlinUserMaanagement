package com.andersen.usermanager.service.impl

import com.andersen.usermanager.dto.request.ClientRequestDTO
import com.andersen.usermanager.dto.response.ClientResponseDTO
import com.andersen.usermanager.entity.Client
import com.andersen.usermanager.entity.Gender
import com.andersen.usermanager.exception.ClientNotFoundException
import com.andersen.usermanager.exception.EmailAlreadyRegisteredException
import com.andersen.usermanager.exception.message.ExceptionMessage
import com.andersen.usermanager.repository.ClientRepository
import com.andersen.usermanager.service.ClientService
import org.springframework.stereotype.Service
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional

@Service
class ClientServiceImpl(var clientRepository: ClientRepository,
                        var genderApiServiceImpl: GenderApiServiceImpl) : ClientService {
    @Transactional
    override fun createClient(client: ClientRequestDTO): ClientResponseDTO {
        val existingClient = clientRepository.findByEmail(client.email)
        if (existingClient != null) {
            throw EmailAlreadyRegisteredException(
                ExceptionMessage.EMAIL_ALREADY_USED
                    .toString()
                    .format(client.email)
            )
        }

        val gender = client.gender ?: Gender.valueOf(genderApiServiceImpl.defineClientGender(client.firstName))
        val save = clientRepository.save(
            Client(
                id = null,
                firstName = client.firstName,
                lastName = client.lastName,
                email = client.email,
                gender = gender,
                job = client.job,
                position = client.position
            )
        )
        return ClientResponseDTO(
            id = save.id!!,
            firstName = save.firstName,
            lastName = save.lastName,
            email = save.email,
            gender = save.gender,
            job = save.job,
            position = save.position
        )
    }

    @Transactional
    override fun updateClient(clientId: Long, clientDTO: ClientRequestDTO): ClientResponseDTO {
        val existingClient = clientRepository.findById(clientId)
            .orElseThrow {
            ClientNotFoundException(
                ExceptionMessage.CLIENT_NOT_FOUND
                    .toString()
                    .format(clientId)
            )
        }

        val existingClientByEmail = clientRepository.findByEmail(clientDTO.email)
        if (existingClientByEmail != null && existingClientByEmail.id != clientId) {
            throw EmailAlreadyRegisteredException(
                ExceptionMessage.EMAIL_ALREADY_USED
                    .toString()
                    .format(clientDTO.email)
            )
        }

        val originalFirstName = existingClient.firstName
        val originalGender = existingClient.gender

        existingClient.apply {
            firstName = clientDTO.firstName
            lastName = clientDTO.lastName
            email = clientDTO.email
            job = clientDTO.job
            position = clientDTO.position
        }

        if (originalFirstName != clientDTO.firstName) {
            val gender = clientDTO.gender ?: Gender.valueOf(
                genderApiServiceImpl.defineClientGender(clientDTO.firstName))
            existingClient.gender = gender
        } else {
            existingClient.gender = originalGender
        }

        val updatedClient = clientRepository.save(existingClient)

        return ClientResponseDTO(
            id = updatedClient.id!!,
            firstName = updatedClient.firstName,
            lastName = updatedClient.lastName,
            email = updatedClient.email,
            gender = updatedClient.gender,
            job = updatedClient.job,
            position = updatedClient.position
        )
    }

    override fun getClient(id: Long): ClientResponseDTO {
        return clientRepository.findById(id)
            .map {
                ClientResponseDTO(
                    id = it.id!!,
                    firstName = it.firstName,
                    lastName = it.lastName,
                    email = it.email,
                    gender = it.gender,
                    job = it.job,
                    position = it.position
                )
            }.orElseThrow {
                ClientNotFoundException(
                    ExceptionMessage.CLIENT_NOT_FOUND
                        .toString()
                        .format(id)
                )
            }
    }

    override fun getAllClients(pageable: Pageable): Page<ClientResponseDTO>{
        val clients = clientRepository.findAll(pageable)
        return clients.map {
            ClientResponseDTO(
                id = it.id!!,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email,
                gender = it.gender,
                job = it.job,
                position = it.position
            )
        }
    }

    override fun getClientsByNames(firstName: String, lastName : String, pageable: Pageable) : Page<ClientResponseDTO>{
        val clients = clientRepository.findByFirstNameAndLastName(firstName, lastName, pageable)
        return clients.map {
            ClientResponseDTO(
                id = it.id!!,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email,
                gender = it.gender,
                job = it.job,
                position = it.position
            )
        }
    }

    override fun findClientsByString(search: String, pageable: Pageable): Page<ClientResponseDTO> {
        val firstSearchSubstring = "%${search.split(" ").first()}%"
        val lastSearchSubstring = "%${search.split(" ").last()}%"

        val clients = clientRepository.findBySearchString(firstSearchSubstring, lastSearchSubstring, pageable)

        return clients.map {
            ClientResponseDTO(
                id = it.id!!,
                firstName = it.firstName,
                lastName = it.lastName,
                email = it.email,
                gender = it.gender,
                job = it.job,
                position = it.position
            )
        }
    }

    @Transactional
    override fun deleteClient(id: Long) {
        clientRepository.deleteById(id)
    }
}