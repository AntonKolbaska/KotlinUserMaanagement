package com.andersen.usermanager.controller.impl

import com.andersen.usermanager.controller.ClientController
import com.andersen.usermanager.dto.request.ClientRequestDTO
import com.andersen.usermanager.dto.response.ClientResponseDTO
import com.andersen.usermanager.service.impl.ClientServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
    override fun getClientList(pageable: Pageable): Page<ClientResponseDTO> {
        return clientServiceImpl.getAllClients(pageable)
    }

    @GetMapping("/search/name")
    override fun getClientListByName(pageable: Pageable,
        @RequestParam firstname: String,
        @RequestParam lastname: String): Page<ClientResponseDTO> {
        return clientServiceImpl.getClientsByNames(firstname, lastname, pageable)
    }

    @GetMapping("/search/string")
    override fun getClientListBySearchString(pageable: Pageable,
                                     @RequestParam searchString: String): Page<ClientResponseDTO> {
        return clientServiceImpl.findClientsByString(searchString, pageable)
    }

    @DeleteMapping("/{id}")
    override fun deleteClient(@PathVariable id: Long) {
        return clientServiceImpl.deleteClient(id)
    }
}