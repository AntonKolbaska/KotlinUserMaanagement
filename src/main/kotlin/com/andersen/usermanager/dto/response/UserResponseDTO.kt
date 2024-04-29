package com.andersen.usermanager.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class UserResponseDTO(
    @JsonProperty("id")
    var id: Long,
    @JsonProperty("email")
    var email: String,
    @JsonProperty("pass")
    var password: String
)