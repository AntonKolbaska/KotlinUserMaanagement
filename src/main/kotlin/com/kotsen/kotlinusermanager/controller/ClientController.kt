package com.kotsen.kotlinusermanager.controller

import com.kotsen.kotlinusermanager.dto.request.ClientRequestDTO
import com.kotsen.kotlinusermanager.dto.response.ClientResponseDTO
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/client")
class ClientController {
    @PutMapping("/create")
    fun createClient(@RequestBody newTask: ClientRequestDTO){

    }

    @GetMapping("/{id}")
    fun getClient(@PathVariable id: Long){

    }

    @GetMapping("/list")
    fun getClientList(){

    }

    @DeleteMapping("/{id}")
    fun deleteClient(@PathVariable id: Long){

    }
}