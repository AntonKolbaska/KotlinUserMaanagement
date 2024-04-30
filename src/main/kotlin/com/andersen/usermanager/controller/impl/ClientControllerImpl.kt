package com.andersen.usermanager.controller.impl

import com.andersen.usermanager.controller.ClientController
import com.andersen.usermanager.dto.request.ClientRequestDTO
import com.andersen.usermanager.dto.response.ClientResponseDTO
import com.andersen.usermanager.service.impl.ClientServiceImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client")
class ClientControllerImpl(var clientServiceImpl: ClientServiceImpl) : ClientController {
    @PostMapping("/create")
    override fun createClient(@Validated @RequestBody newClient: ClientRequestDTO): ClientResponseDTO {
        return clientServiceImpl.createClient(newClient)
    }

    @PutMapping("/update/{id}")
    override fun updateClient(@PathVariable id: Long,
                              @Validated @RequestBody newClient: ClientRequestDTO): ClientResponseDTO {
        return clientServiceImpl.updateClient(id, newClient)
    }

    @GetMapping("/{id}")
    override fun getClient(@PathVariable id: Long): ClientResponseDTO {
        return clientServiceImpl.getClient(id)
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