package com.kotsen.kotlinusermanager.controller

import com.kotsen.kotlinusermanager.dto.request.ClientRequestDTO
import com.kotsen.kotlinusermanager.dto.response.ClientResponseDTO
import com.kotsen.kotlinusermanager.service.ClientServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/client")
class ClientController (var clientServiceImpl: ClientServiceImpl){
    @PutMapping("/create")
    fun createClient(@RequestBody newClient: ClientRequestDTO): ClientResponseDTO{
        return clientServiceImpl.createClient(newClient)
    }

    @GetMapping("/{id}")
    fun getClient(@PathVariable id: Long): ClientResponseDTO{
        return clientServiceImpl.getClient(id)
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found")
    }

    @GetMapping("/list")
    fun getClientList(): List<ClientResponseDTO>{
        return clientServiceImpl.getAllClients()
    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long){
        return clientServiceImpl.deleteClient(id)
    }
}