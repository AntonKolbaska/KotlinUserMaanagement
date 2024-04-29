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
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mock
import org.mockito.Mockito.times
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
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
    fun createClient_WhenEmailIsAvailable_ShouldCreateClient() {
        val clientRequestDTO = TestDataFactory.createGenderDefinedClientRequestDTO()
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

}