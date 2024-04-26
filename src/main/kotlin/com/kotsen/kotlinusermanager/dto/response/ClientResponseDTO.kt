package com.kotsen.kotlinusermanager.dto.response

import com.fasterxml.jackson.annotation.JsonProperty

class ClientResponseDTO(

        @JsonProperty("id")
        var id: Long,
        @JsonProperty("firstname")
        var firstName: String,
        @JsonProperty("lastname")
        var lastName: String,
        @JsonProperty("email")
        var email: String
        )