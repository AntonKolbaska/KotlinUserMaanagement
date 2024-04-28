package com.andersen.usermanager.controller.impl

import com.andersen.usermanager.controller.ClientController
import com.andersen.usermanager.dto.request.ClientRequestDTO
import com.andersen.usermanager.dto.response.ClientResponseDTO
import com.andersen.usermanager.service.ClientServiceImpl
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/client")
class ClientControllerImpl(var clientServiceImpl: ClientServiceImpl) : ClientController {
    @PostMapping("/create")
    override fun createClient(@RequestBody newClient: ClientRequestDTO): ClientResponseDTO {
        return clientServiceImpl.createClient(newClient)
    }

    @PutMapping("/update/{id}")
    override fun updateClient(@PathVariable id: Long, @RequestBody newClient: ClientRequestDTO): ClientResponseDTO {
        return clientServiceImpl.updateClient(id, newClient)
    }

    @GetMapping("/{id}")
    override fun getClient(@PathVariable id: Long): ClientResponseDTO {
        return clientServiceImpl.getClient(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "Client not found")
    }

    @GetMapping("/list")
    override fun getClientList(): List<ClientResponseDTO> {
        return clientServiceImpl.getAllClients()
    }

    @GetMapping("/search/name")
    override fun getClientListByName(
        @RequestParam firstname: String,
        @RequestParam lastname: String): List<ClientResponseDTO> {
        return clientServiceImpl.getClientsByNames(firstname, lastname)
    }

    @DeleteMapping("/{id}")
    override fun deleteClient(@PathVariable id: Long) {
        return clientServiceImpl.deleteClient(id)
    }
}