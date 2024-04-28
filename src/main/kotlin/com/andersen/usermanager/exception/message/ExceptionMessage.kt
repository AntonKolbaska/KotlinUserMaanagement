package com.andersen.usermanager.exception.message

enum class ExceptionMessage(private val message: String) {

    CLIENT_NOT_FOUND("Client with id %s not found"),
    GENDER_NOT_DEFINED("Gender of client &s cannot be defined"),
    NO_CLIENTS_EXIST("No clients exist");

    override fun toString(): String {
        return message
    }
}