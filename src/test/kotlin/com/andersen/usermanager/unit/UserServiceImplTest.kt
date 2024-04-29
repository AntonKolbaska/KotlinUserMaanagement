package com.andersen.usermanager.unit

import com.andersen.usermanager.dto.request.AuthenticationRequestDTO
import com.andersen.usermanager.dto.request.UserRequestDTO
import com.andersen.usermanager.entity.User
import com.andersen.usermanager.exception.EmailAlreadyRegisteredException
import com.andersen.usermanager.exception.UserNotFoundException
import com.andersen.usermanager.repository.UserRepository
import com.andersen.usermanager.service.impl.JwtServiceImpl
import com.andersen.usermanager.service.impl.UserServiceImpl
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceImplTest {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var authenticationManager: AuthenticationManager

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @Mock
    private lateinit var jwtServiceImpl: JwtServiceImpl

    private lateinit var userServiceImpl: UserServiceImpl

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        userServiceImpl = UserServiceImpl(userRepository, authenticationManager, passwordEncoder, jwtServiceImpl)
    }

    @Test
    fun createUser_WhenEmailIsAvailable_ShouldCreateUser() {
        val userRequestDTO = UserRequestDTO("test@example.com", "password")
        val encodedPassword = "encodedPassword"

        `when`(userRepository.findByEmail(userRequestDTO.email)).thenReturn(null)
        `when`(passwordEncoder.encode(userRequestDTO.password)).thenReturn(encodedPassword)
        `when`(userRepository.save(User(null, userRequestDTO.email, encodedPassword)))
            .thenReturn(User(1L, userRequestDTO.email, encodedPassword))

        val userResponseDTO = userServiceImpl.createUser(userRequestDTO)

        assertEquals(userRequestDTO.email, userResponseDTO.email)
        assertEquals(encodedPassword, userResponseDTO.password)
    }

    @Test
    fun createUser_WhenEmailIsAlreadyRegistered_ShouldThrowException() {
        val userRequestDTO = UserRequestDTO("test@example.com", "password")
        val existingUser = User(1L, userRequestDTO.email, "encodedPassword")

        `when`(userRepository.findByEmail(userRequestDTO.email)).thenReturn(existingUser)

        assertThrows(EmailAlreadyRegisteredException::class.java) {
            userServiceImpl.createUser(userRequestDTO)
        }
    }

    @Test
    fun authenticateUser_WhenUserExistsAndCredentialsAreValid_ShouldReturnAuthToken() {
        val authenticationRequestDTO = AuthenticationRequestDTO("test@example.com", "password")
        val encodedPassword = "encodedPassword"
        val user = User(1L, authenticationRequestDTO.email, encodedPassword)
        val authToken = "authToken"

        `when`(authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.email,
                authenticationRequestDTO.password
            )
        )).thenReturn(mock(Authentication::class.java))
        `when`(userRepository.findByEmail(authenticationRequestDTO.email)).thenReturn(user)
        `when`(jwtServiceImpl.generateToken(user)).thenReturn(authToken)

        val authenticationResponseDTO = userServiceImpl.authenticateUser(authenticationRequestDTO)

        assertEquals(authToken, authenticationResponseDTO.token)
    }

    @Test
    fun authenticateUser_WhenUserDoesNotExist_ShouldThrowException() {
        val authenticationRequestDTO = AuthenticationRequestDTO("test@example.com", "password")

        `when`(authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authenticationRequestDTO.email,
                authenticationRequestDTO.password
            )
        )).thenReturn(mock(Authentication::class.java))
        `when`(userRepository.findByEmail(authenticationRequestDTO.email)).thenReturn(null)

        assertThrows(UserNotFoundException::class.java) {
            userServiceImpl.authenticateUser(authenticationRequestDTO)
        }
    }

}