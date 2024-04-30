package com.andersen.usermanager.dto.request

import com.andersen.usermanager.entity.Gender
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Pattern

class ClientRequestDTO(
    @JsonProperty("firstname")
    var firstName: String,
    @JsonProperty("lastname")
    var lastName: String,
    @JsonProperty("email")
    @field:Email(message = "Invalid email format")
    var email: String,
    @JsonProperty("gender")
    @field:Pattern(regexp = "^(MALE|FEMALE)$", message = "Gender must be MALE or FEMALE")
    var gender: Gender?,
    @JsonProperty("job")
    var job: String?,
    @JsonProperty("position")
    var position: String?
)