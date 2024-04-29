package com.andersen.usermanager.controller

import com.andersen.usermanager.configuration.swagger.SwaggerService
import com.andersen.usermanager.dto.request.AuthenticationRequestDTO
import com.andersen.usermanager.dto.request.UserRequestDTO
import com.andersen.usermanager.dto.response.AuthenticationResponseDTO
import com.andersen.usermanager.dto.response.UserResponseDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.web.bind.annotation.RequestBody

interface UserController {
    @Operation(summary = "Create user",
        description = "Retrieve and save new user credentials")
    @SwaggerService
    fun createUser(@RequestBody user: UserRequestDTO): UserResponseDTO

    @Operation(summary = "Authenticate user",
        description = "Authenticate user with provided creds and get access token")
    @SwaggerService
    fun authenticateUser(@RequestBody credentials: AuthenticationRequestDTO): AuthenticationResponseDTO
}