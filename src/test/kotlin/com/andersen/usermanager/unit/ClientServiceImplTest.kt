package com.andersen.usermanager.unit

import com.andersen.usermanager.data.TestDataFactory
import com.andersen.usermanager.entity.Client
import com.andersen.usermanager.exception.ClientNotFoundException
import com.andersen.usermanager.exception.EmailAlreadyRegisteredException
import com.andersen.usermanager.repository.ClientRepository
import com.andersen.usermanager.service.impl.ClientServiceImpl
import com.andersen.usermanager.service.impl.GenderApiServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.never
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import java.util.Optional

class ClientServiceImplTest {

    @Mock
    private lateinit var clientRepository: ClientRepository

    @Mock
    private lateinit var genderApiServiceImpl: GenderApiServiceImpl

    private lateinit var clientServiceImpl: ClientServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        clientServiceImpl = ClientServiceImpl(clientRepository, genderApiServiceImpl)
    }

    @Test
    fun createClient_GenderNotSet_ShouldCreateClient() {
        val clientRequestDTO = TestDataFactory.createGenderNotDefinedClientRequestDTO()
        val client = TestDataFactory.createClient()

        `when`(clientRepository.findByEmail(clientRequestDTO.email)).thenReturn(null)
        `when`(genderApiServiceImpl.defineClientGender(clientRequestDTO.firstName)).thenReturn("MALE")
        `when`(clientRepository.save(any(Client::class.java))).thenAnswer { invocation ->
            val savedClient = invocation.getArgument<Client>(0)
            savedClient.id = 1L
            savedClient
        }

        val clientResponseDTO = clientServiceImpl.createClient(clientRequestDTO)

        assertEquals(client.id, clientResponseDTO.id)
        assertEquals(client.firstName, clientResponseDTO.firstName)
        assertEquals(client.lastName, clientResponseDTO.lastName)
        assertEquals(client.email, clientResponseDTO.email)
        assertEquals(client.gender, clientResponseDTO.gender)
        assertEquals(client.job, clientResponseDTO.job)
        assertEquals(client.position, clientResponseDTO.position)
    }

    @Test
    fun createClient_GenderSet_ShouldCreateClient() {
        val clientRequestDTO = TestDataFactory.createGenderDefinedClientRequestDTO()
        val client = TestDataFactory.createClient()

        `when`(clientRepository.findByEmail(clientRequestDTO.email)).thenReturn(null)
        `when`(clientRepository.save(any(Client::class.java))).thenAnswer { invocation ->
            val savedClient = invocation.getArgument<Client>(0)
            savedClient.id = 1L
            savedClient
        }

        val clientResponseDTO = clientServiceImpl.createClient(clientRequestDTO)

        assertEquals(client.id, clientResponseDTO.id)
        assertEquals(client.firstName, clientResponseDTO.firstName)
        assertEquals(client.lastName, clientResponseDTO.lastName)
        assertEquals(client.email, clientResponseDTO.email)
        assertEquals(client.gender, clientResponseDTO.gender)
        assertEquals(client.job, clientResponseDTO.job)
        assertEquals(client.position, clientResponseDTO.position)
    }

    @Test
    fun createClient_WhenEmailIsAvailable_ShouldCreateClient() {
        val clientRequestDTO = TestDataFactory.createGenderNotDefinedClientRequestDTO()
        val client = TestDataFactory.createClient()

        `when`(clientRepository.findByEmail(clientRequestDTO.email)).thenReturn(null)
        `when`(genderApiServiceImpl.defineClientGender(clientRequestDTO.firstName)).thenReturn("MALE")
        `when`(clientRepository.save(any(Client::class.java))).thenAnswer { invocation ->
            val savedClient = invocation.getArgument<Client>(0)
            savedClient.id = 1L
            savedClient
        }

        val clientResponseDTO = clientServiceImpl.createClient(clientRequestDTO)

        assertEquals(client.id, clientResponseDTO.id)
        assertEquals(client.firstName, clientResponseDTO.firstName)
        assertEquals(client.lastName, clientResponseDTO.lastName)
        assertEquals(client.email, clientResponseDTO.email)
        assertEquals(client.gender, clientResponseDTO.gender)
        assertEquals(client.job, clientResponseDTO.job)
        assertEquals(client.position, clientResponseDTO.position)
    }

    @Test
    fun createClient_WhenEmailIsAlreadyRegistered_ShouldThrowException() {
        val clientRequestDTO = TestDataFactory.createGenderDefinedClientRequestDTO()
        val existingClient = TestDataFactory.createClient()

        `when`(clientRepository.findByEmail(clientRequestDTO.email)).thenReturn(existingClient)

        assertThrows(EmailAlreadyRegisteredException::class.java) {
            clientServiceImpl.createClient(clientRequestDTO)
        }
    }

    @Test
    fun updateClient_WhenClientExistsAndEmailIsAvailable_ShouldUpdateClient() {
        val clientId = 1L
        val clientRequestDTO = TestDataFactory.createGenderDefinedClientRequestDTO()
        val existingClient = TestDataFactory.createClient()
        val updatedClient = existingClient.copy()

        `when`(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient))
        `when`(clientRepository.findByEmail(clientRequestDTO.email)).thenReturn(null)
        `when`(genderApiServiceImpl.defineClientGender(clientRequestDTO.firstName)).thenReturn("MALE")
        `when`(clientRepository.save(existingClient)).thenReturn(updatedClient)

        val clientResponseDTO = clientServiceImpl.updateClient(clientId, clientRequestDTO)

        assertEquals(updatedClient.id, clientResponseDTO.id)
        assertEquals(updatedClient.firstName, clientResponseDTO.firstName)
        assertEquals(updatedClient.lastName, clientResponseDTO.lastName)
        assertEquals(updatedClient.email, clientResponseDTO.email)
        assertEquals(updatedClient.gender, clientResponseDTO.gender)
        assertEquals(updatedClient.job, clientResponseDTO.job)
        assertEquals(updatedClient.position, clientResponseDTO.position)

        verify(clientRepository, times(1)).findById(clientId)
        verify(clientRepository, times(1)).findByEmail(clientRequestDTO.email)
        verify(genderApiServiceImpl, times(0)).defineClientGender(clientRequestDTO.firstName)
        verify(clientRepository, times(1)).save(existingClient)
    }

    @Test
    fun updateClient_WhenClientDoesNotExist_ShouldThrowException() {
        val clientId = 1L
        val clientRequestDTO = TestDataFactory.createGenderDefinedClientRequestDTO()

        `when`(clientRepository.findById(clientId)).thenReturn(Optional.empty())

        assertThrows(ClientNotFoundException::class.java) {
            clientServiceImpl.updateClient(clientId, clientRequestDTO)
        }
    }

    @Test
    fun updateClient_WhenClientEmailAlreadyUsed_ShouldThrowEmailAlreadyRegisteredException() {
        val clientId = 1L
        val clientDTO = TestDataFactory.createGenderDefinedClientRequestDTO()
        val existingClient = TestDataFactory.createClient()
        val existingClientByEmail = TestDataFactory.createClient_1()

        `when`(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient))
        `when`(clientRepository.findByEmail(clientDTO.email)).thenReturn(existingClientByEmail)

        assertThrows<EmailAlreadyRegisteredException> {
            clientServiceImpl.updateClient(clientId, clientDTO)
        }

        verify(clientRepository, times(1)).findById(clientId)
        verify(clientRepository, times(1)).findByEmail(clientDTO.email)
        verify(clientRepository, never()).save(any())
    }

    @Test
    fun deleteClient_WhenClientExists_ShouldCallDeleteByIdOnRepository() {
        val clientId = 1L

        clientServiceImpl.deleteClient(clientId)

        verify(clientRepository, times(1)).deleteById(clientId)
    }

    @Test
    fun getClient_WhenClientExists_ShouldReturnClientResponseDTO() {
        val clientId = 1L
        val client = TestDataFactory.createClient()

        `when`(clientRepository.findById(clientId)).thenReturn(Optional.of(client))

        val clientResponseDTO = clientServiceImpl.getClient(clientId)

        assertEquals(client.id, clientResponseDTO.id)
        assertEquals(client.firstName, clientResponseDTO.firstName)
        assertEquals(client.lastName, clientResponseDTO.lastName)
        assertEquals(client.email, clientResponseDTO.email)
        assertEquals(client.gender, clientResponseDTO.gender)
        assertEquals(client.job, clientResponseDTO.job)
        assertEquals(client.position, clientResponseDTO.position)

        verify(clientRepository, times(1)).findById(clientId)
    }

    @Test
    fun getClient_WhenClientDoesNotExist_ShouldThrowClientNotFoundException() {
        val clientId = 1L

        `when`(clientRepository.findById(clientId)).thenReturn(Optional.empty())

        assertThrows<ClientNotFoundException> {
            clientServiceImpl.getClient(clientId)
        }

        verify(clientRepository, times(1)).findById(clientId)
    }

    @Test
    fun getAllClients_WhenClientsExist_ShouldReturnPageOfClientResponseDTO() {
        val pageable = PageRequest.of(0, 10)
        val clients = TestDataFactory.createListOfClients(20)
        val clientsPage = PageImpl(clients, pageable, clients.size.toLong())

        `when`(clientRepository.findAll(pageable)).thenReturn(clientsPage)

        val resultPage = clientServiceImpl.getAllClients(pageable)

        assertEquals(clients.size.toLong(), resultPage.totalElements)
        assertEquals(clients.size, resultPage.content.size)

        for (i in clients.indices) {
            val expectedClient = clients[i]
            val actualClient = resultPage.content[i]

            assertEquals(expectedClient.id, actualClient.id)
            assertEquals(expectedClient.firstName, actualClient.firstName)
            assertEquals(expectedClient.lastName, actualClient.lastName)
            assertEquals(expectedClient.email, actualClient.email)
            assertEquals(expectedClient.gender, actualClient.gender)
            assertEquals(expectedClient.job, actualClient.job)
            assertEquals(expectedClient.position, actualClient.position)
        }

        verify(clientRepository, times(1)).findAll(pageable)
    }

    @Test
    fun getClientsByNames_WhenClientsExist_ShouldReturnMatchingClients() {
        val firstName = "J"
        val lastName = "D"
        val pageable = PageRequest.of(0, 10)

        val client1 = TestDataFactory.createClient()
        val client2 = TestDataFactory.createClient_1()

        val clients = listOf(client1, client2)
        val page = PageImpl(clients)

        `when`(clientRepository.findByFirstNameAndLastName(firstName, lastName, pageable)).thenReturn(page)

        val result = clientServiceImpl.getClientsByNames(firstName, lastName, pageable)

        assertEquals(page.totalElements, result.totalElements)
        assertEquals(page.number, result.number)
        assertEquals(page.size, result.size)

        assertEquals(client1.id, result.content[0].id)
        assertEquals(client1.firstName, result.content[0].firstName)
        assertEquals(client1.lastName, result.content[0].lastName)
        assertEquals(client1.email, result.content[0].email)
        assertEquals(client1.gender, result.content[0].gender)
        assertEquals(client1.job, result.content[0].job)
        assertEquals(client1.position, result.content[0].position)

        assertEquals(client2.id, result.content[1].id)
        assertEquals(client2.firstName, result.content[1].firstName)
        assertEquals(client2.lastName, result.content[1].lastName)
        assertEquals(client2.email, result.content[1].email)
        assertEquals(client2.gender, result.content[1].gender)
        assertEquals(client2.job, result.content[1].job)
        assertEquals(client2.position, result.content[1].position)

        verify(clientRepository, times(1)).findByFirstNameAndLastName(firstName, lastName, pageable)
    }

    @Test
    fun getClientsByNames_WhenNoClientsExist_ShouldReturnEmptyPage() {
        val firstName = "Nonexistent"
        val lastName = "User"
        val pageable = PageRequest.of(0, 10)

        val page = PageImpl(emptyList<Client>())

        `when`(clientRepository.findByFirstNameAndLastName(firstName, lastName, pageable)).thenReturn(page)

        val result = clientServiceImpl.getClientsByNames(firstName, lastName, pageable)

        assertEquals(0, result.totalElements)
        assertEquals(0, result.number)
        assertEquals(0, result.size)
        assertTrue(result.content.isEmpty())

        verify(clientRepository, times(1)).findByFirstNameAndLastName(firstName, lastName, pageable)
    }

}