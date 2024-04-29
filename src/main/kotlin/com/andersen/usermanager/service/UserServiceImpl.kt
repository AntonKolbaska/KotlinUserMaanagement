package com.andersen.usermanager.service

import com.andersen.usermanager.dto.request.AuthenticationRequestDTO
import com.andersen.usermanager.dto.request.UserRequestDTO
import com.andersen.usermanager.dto.response.AuthenticationResponseDTO
import com.andersen.usermanager.dto.response.UserResponseDTO
import com.andersen.usermanager.entity.User
import com.andersen.usermanager.exception.EmailAlreadyRegisteredException
import com.andersen.usermanager.exception.UserNotFoundException
import com.andersen.usermanager.exception.message.ExceptionMessage
import com.andersen.usermanager.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    var userRepository: UserRepository, var authenticationManager: AuthenticationManager,
    var passwordEncoder: PasswordEncoder, var jwtServiceImpl: JwtServiceImpl
) {

    @Transactional
    fun createUser(user: UserRequestDTO): UserResponseDTO {
        val existingUser = userRepository.findByEmail(user.email)
        if (existingUser != null) {
            throw EmailAlreadyRegisteredException(
                ExceptionMessage.EMAIL_ALREADY_USED
                    .toString()
                    .format(user.email)
            )
        }
        val save = userRepository.save(
            User(
                id = null,
                email = user.email,
                pass = passwordEncoder.encode(user.password),
            )
        )
        return UserResponseDTO(
            id = save.id!!,
            email = save.email,
            password = save.pass
        )
    }

    fun authenticateUser(request: AuthenticationRequestDTO): AuthenticationResponseDTO {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(request.email, request.password)
        )

        val user = userRepository.findByEmail(request.email)
            ?: throw UserNotFoundException(
                ExceptionMessage.USER_NOT_FOUND
                    .toString()
                    .format(request.email)
            )

        val jwtToken = jwtServiceImpl.generateToken(user)

        return AuthenticationResponseDTO(
            token = jwtToken
        )
    }

}