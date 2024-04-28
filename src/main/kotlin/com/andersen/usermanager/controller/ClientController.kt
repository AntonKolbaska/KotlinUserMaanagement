package com.andersen.usermanager.controller

import com.andersen.usermanager.configuration.swagger.SwaggerService
import com.andersen.usermanager.dto.request.ClientRequestDTO
import com.andersen.usermanager.dto.response.ClientResponseDTO
import io.swagger.v3.oas.annotations.Operation
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam

interface ClientController {
    @Operation(summary = "Create user",
        description = "Retrieve and save new user information")
    @SwaggerService
    fun createClient(@RequestBody newClient: ClientRequestDTO): ClientResponseDTO

    @SwaggerService
    fun updateClient(@PathVariable id: Long, @RequestBody newClient: ClientRequestDTO): ClientResponseDTO

    @SwaggerService
    fun getClient(@PathVariable id: Long): ClientResponseDTO

    @SwaggerService
    fun getClientList(pageable: Pageable): Page<ClientResponseDTO>

    @SwaggerService
    fun getClientListByName(pageable: Pageable,
        @RequestParam firstname: String,
        @RequestParam lastname: String
    ): Page<ClientResponseDTO>

    @SwaggerService
    fun deleteClient(@PathVariable id: Long)
}