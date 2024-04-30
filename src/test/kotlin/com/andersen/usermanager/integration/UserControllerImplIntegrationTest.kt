package com.andersen.usermanager.integration

import com.andersen.usermanager.config.TestDatabaseConfig
import com.andersen.usermanager.data.TestDataFactory
import com.andersen.usermanager.dto.request.AuthenticationRequestDTO
import com.andersen.usermanager.dto.response.AuthenticationResponseDTO
import com.andersen.usermanager.dto.response.UserResponseDTO
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(properties = ["server.port=8080"])
@AutoConfigureMockMvc
@ContextConfiguration(classes = [TestDatabaseConfig::class])
class UserControllerImplIntegrationTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Test
    fun createUser_WhenValidUserRequest_ShouldReturnCreatedUser() {
        val userRequest = TestDataFactory.createUserRequestDTO()

        val result = mockMvc.perform(
            post("/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userRequest))
        )
            .andExpect(status().isOk)
            .andReturn()

        val response = objectMapper.readValue(result.response.contentAsString, UserResponseDTO::class.java)

        assertNotNull(response.id)
        assertEquals(userRequest.email, response.email)
    }

    @Test
    fun authenticateUser_WhenValidAuthenticationRequest_ShouldReturnAuthenticationResponse() {
        val authenticationRequest = TestDataFactory.createAuthenticationRequestDTO()

        val result = mockMvc.perform(
            post("/user/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(authenticationRequest))
        )
            .andExpect(status().isOk)
            .andReturn()

        val response = objectMapper.readValue(result.response.contentAsString, AuthenticationResponseDTO::class.java)

        assertNotNull(response.token)
    }
}