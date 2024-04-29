package com.andersen.usermanager.controller.impl

import com.andersen.usermanager.dto.request.AuthenticationRequestDTO
import com.andersen.usermanager.dto.request.UserRequestDTO
import com.andersen.usermanager.dto.response.AuthenticationResponseDTO
import com.andersen.usermanager.dto.response.UserResponseDTO
import com.andersen.usermanager.service.UserServiceImpl
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class UserControllerImpl(var userService: UserServiceImpl) {

    @PostMapping("/create")
    fun createUser(@RequestBody newUser: UserRequestDTO): UserResponseDTO {
        return userService.createUser(newUser)
    }

    @PostMapping("/authenticate")
    fun authenticateUser(@RequestBody newUser: AuthenticationRequestDTO): AuthenticationResponseDTO {
        return userService.authenticateUser(newUser)
    }
}