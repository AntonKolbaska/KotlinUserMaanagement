package com.andersen.usermanager.controller.impl

import com.andersen.usermanager.controller.UserController
import com.andersen.usermanager.dto.request.AuthenticationRequestDTO
import com.andersen.usermanager.dto.request.UserRequestDTO
import com.andersen.usermanager.dto.response.AuthenticationResponseDTO
import com.andersen.usermanager.dto.response.UserResponseDTO
import com.andersen.usermanager.service.impl.UserServiceImpl
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/user")
class UserControllerImpl(var userService: UserServiceImpl) : UserController{

    @PostMapping("/create")
    override fun createUser(@Valid @RequestBody newUser: UserRequestDTO): UserResponseDTO {
        return userService.createUser(newUser)
    }

    @PostMapping("/authenticate")
    override fun authenticateUser(@Valid @RequestBody newUser: AuthenticationRequestDTO): AuthenticationResponseDTO {
        return userService.authenticateUser(newUser)
    }
}