package com.andersen.usermanager.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

class AuthenticationRequestDTO (
    @JsonProperty("email")
    var email: String,
    @JsonProperty("pass")
    var password: String
)