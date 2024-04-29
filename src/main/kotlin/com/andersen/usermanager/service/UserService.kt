package com.andersen.usermanager.service

import com.andersen.usermanager.dto.request.AuthenticationRequestDTO
import com.andersen.usermanager.dto.request.UserRequestDTO
import com.andersen.usermanager.dto.response.AuthenticationResponseDTO
import com.andersen.usermanager.dto.response.UserResponseDTO

interface UserService {
    fun createUser(user: UserRequestDTO): UserResponseDTO
    fun authenticateUser(request: AuthenticationRequestDTO): AuthenticationResponseDTO
}