package com.andersen.usermanager.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class AuthenticationResponseDTO(
    @JsonProperty("token")
    var token: String,
)