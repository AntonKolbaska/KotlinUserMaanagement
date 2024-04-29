package com.andersen.usermanager.exception.message

enum class ExceptionMessage(private val message: String) {

    CLIENT_NOT_FOUND("Client with id %s not found"),
    USER_NOT_FOUND("User with email %s not found"),
    GENDER_NOT_DEFINED("Gender not detected"),
    EMAIL_ALREADY_USED("Email %s is already registered"),
    NO_CLIENTS_EXIST("No clients exist");

    override fun toString(): String {
        return message
    }
}