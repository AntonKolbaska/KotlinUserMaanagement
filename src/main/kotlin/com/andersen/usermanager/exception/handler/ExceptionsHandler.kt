package com.andersen.usermanager.exception.handler

import com.andersen.usermanager.exception.ClientNotFoundException
import com.andersen.usermanager.exception.EmailAlreadyRegisteredException
import com.andersen.usermanager.exception.GenderUndefinedException
import com.andersen.usermanager.exception.NoClientsExistException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionsHandler {
    @ExceptionHandler
    fun handleClientNotFoundException(exception: ClientNotFoundException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.message)
    }

    @ExceptionHandler
    fun handleNoClientsExistException(exception: NoClientsExistException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(exception.message)
    }

    @ExceptionHandler
    fun handleGenderUndefinedException(exception: GenderUndefinedException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
    }

    @ExceptionHandler
    fun handleEmailAlreadyRegisteredException(exception: EmailAlreadyRegisteredException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
    }
}