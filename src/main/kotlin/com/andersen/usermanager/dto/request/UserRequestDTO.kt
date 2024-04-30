package com.andersen.usermanager.dto.request

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email

class UserRequestDTO(
    @JsonProperty("email")
    @field:Email(message = "Invalid email format")
    var email: String,
    @JsonProperty("pass")
    var password: String
)