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
    @Operation(summary = "Create client",
        description = "Retrieve and save new client information")
    @SwaggerService
    fun createClient(@RequestBody newClient: ClientRequestDTO): ClientResponseDTO

    @Operation(summary = "Update client",
        description = "Retrieve and update client information by id")
    @SwaggerService
    fun updateClient(@PathVariable id: Long,
                     @RequestBody newClient: ClientRequestDTO): ClientResponseDTO

    @Operation(summary = "Find client",
        description = "Find client by id")
    @SwaggerService
    fun getClient(@PathVariable id: Long): ClientResponseDTO

    @Operation(summary = "Find all clients",
        description = "Get list of all registered clients")
    @SwaggerService
    fun getClientList(pageable: Pageable): Page<ClientResponseDTO>

    @Operation(summary = "Find clients by name",
        description = "Get list of clients with names like provided (ordered: first name, last name)")
    @SwaggerService
    fun getClientListByName(pageable: Pageable,
        @RequestParam firstname: String,
        @RequestParam lastname: String
    ): Page<ClientResponseDTO>

    @Operation(summary = "Delete client",
        description = "Delete designated client by id")
    @SwaggerService
    fun deleteClient(@PathVariable id: Long)

    @Operation(summary = "Find client by search string",
        description = "Get list of clients that have matching names (unordered)")
    @SwaggerService
    fun getClientListBySearchString(pageable: Pageable,
                                    @RequestParam searchString: String): Page<ClientResponseDTO>
}